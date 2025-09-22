package groovebox.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import groovebox.adapter.JavaMidiNote;
import groovebox.adapter.JavaMidiSequence;

public class Beat<T extends List<Phrase>> {
	private final JavaMidiSequence sequence = new JavaMidiSequence();
	private final T phrases;

	public Beat(Supplier<T> phrasesSupplier) {
		phrases = phrasesSupplier.get();
		addPhrase();
	}

	public T getPhrases() {
		return phrases;
	}

	public void addPhrase() {
		phrases.add(new PhraseImpl(phrases.size()));
		updateTrackEnd();
	}

	@SuppressWarnings("unchecked")
	public void removePhrase(int indexToBeRemoved) {
		Phrase phraseToBeRemoved = phrases.remove(indexToBeRemoved);
		((PhraseImpl)phraseToBeRemoved).removed();
		for (int i = indexToBeRemoved; i < phrases.size(); i++) {
			PhraseImpl successorPhrase = (PhraseImpl) phrases.get(i);
			successorPhrase.setPhrasePos(i);
		}
		updateTrackEnd();
	}

	private void updateTrackEnd() {
		sequence.setTrackEnd(sumTicks());
	}

	private int sumTicks() {
		return phrases.stream()
				.map(Phrase::getNotes)
				.flatMap(Collection::stream)
				.map(Note::getTicks)
				.mapToInt(List::size)
				.sum();
	}

	JavaMidiSequence getSequence() {
		return sequence;
	}

	private class PhraseImpl implements Phrase {
		private int phrasePos = 0;
		private final List<NoteImpl> notes = List.of(
				new NoteImpl(0),
				new NoteImpl(1),
				new NoteImpl(2),
				new NoteImpl(3)
		);

		public PhraseImpl(int phrasePos) {
			setPhrasePos(phrasePos);
		}

		private void setPhrasePos(int phrasePos) {
			this.phrasePos = phrasePos;
			notes.forEach(note -> note.ticks.forEach(NoteImpl.TickImpl::updatePos));
		}

		private void removed() {
			notes.forEach(note -> note.ticks.forEach(NoteImpl.TickImpl::removeFromTrack));
		}

		@Override
		public List<? extends Note> getNotes() {
			return notes;
		}

		private class NoteImpl implements Note {
			private final List<TickImpl> ticks = List.of(
					new TickImpl(0),
					new TickImpl(1),
					new TickImpl(2),
					new TickImpl(3)
			);
			private final int notePos;

			public NoteImpl(int notePos) {
				this.notePos = notePos;
			}

			@Override
			public List<? extends Tick> getTicks() {
				return ticks;
			}

			private class TickImpl implements Tick {

				private final int tickPos;

				private final Map<Instrument, JavaMidiNote> instrumentTable = new HashMap<>();

				private TickImpl(int tickPos) {
					this.tickPos = tickPos;
				}

				@Override
				public InstrumentDataApi getInstrumentData(Instrument instrument) {
					return new InstrumentDataImpl(getInstrument(instrument));
				}

				private JavaMidiNote getInstrument(Instrument instrument) {
					return instrumentTable.computeIfAbsent(instrument, this::createNoteEvent);
				}

				private JavaMidiNote createNoteEvent(Instrument instrument) {
					return sequence.getNote(instrument.value, getTickVal());
				}

				private void updatePos() {
					final int tickVal = getTickVal();
					instrumentTable.values()
							.stream()
							.filter(noteEventWrapper -> noteEventWrapper.getTickVal() != tickVal)
							.forEach(noteEventWrapper -> noteEventWrapper.setTickVal(tickVal));
				}

				private int getTickVal() {
					int phraseOffset = notes.size() * ticks.size() * phrasePos;
					int noteOffset = ticks.size() * notePos;
					return phraseOffset + noteOffset + tickPos;
				}

				private void removeFromTrack() {
					instrumentTable.values().forEach(note -> note.setAddedToTrack(false));
				}
			}
		}
	}

	private record InstrumentDataImpl(JavaMidiNote noteEvent) implements InstrumentDataApi {

		@Override
		public boolean isActive() {
			return noteEvent.isAddedToTrack();
		}

		@Override
		public void setActive(boolean active) {
			noteEvent.setAddedToTrack(active);
		}

		@Override
		public int getVelocity() {
			return noteEvent.getVelocity();
		}

		@Override
		public void setVelocity(int velocity) {
			noteEvent.setVelocity(velocity);
		}
	}
}
