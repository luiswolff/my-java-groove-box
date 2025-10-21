package groovebox.ui.model;

import java.util.Collections;
import java.util.List;

import groovebox.service.Instrument;
import groovebox.service.Phrase;

public class ShownInstrumentRowModel {
	private final List<ShownInstrumentNoteModel> shownNotes;

	ShownInstrumentRowModel(Instrument instrument, Phrase phrase) {
		if (phrase == null) {
			shownNotes = Collections.emptyList();
		}  else {
			shownNotes = phrase.getNotes().stream()
					.map(note -> new ShownInstrumentNoteModel(instrument, note))
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
