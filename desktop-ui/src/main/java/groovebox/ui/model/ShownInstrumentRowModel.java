package groovebox.ui.model;

import java.util.Collections;
import java.util.List;

import groovebox.service.Instrument;
import groovebox.service.Phrase;
import groovebox.ui.InstrumentTickPositionEnum;

public class ShownInstrumentRowModel {
	private final List<ShownInstrumentNoteModel> shownNotes;

	ShownInstrumentRowModel(Instrument instrument, Phrase phrase, InstrumentTickPositionEnum position) {
		if (phrase == null) {
			shownNotes = Collections.emptyList();
		}  else {
			shownNotes = phrase.getNotes().stream()
					.map(note -> new ShownInstrumentNoteModel(instrument, note, position))
					.toList();
		}
	}

	public List<ShownInstrumentNoteModel> getShownNotes() {
		return shownNotes;
	}

	public void destroy() {
		shownNotes.forEach(ShownInstrumentNoteModel::destroy);
	}
}
