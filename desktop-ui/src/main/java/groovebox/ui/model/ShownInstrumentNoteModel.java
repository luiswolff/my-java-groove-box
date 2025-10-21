package groovebox.ui.model;

import java.util.List;

import groovebox.service.Instrument;
import groovebox.service.Note;

public class ShownInstrumentNoteModel {
	private final List<ShownInstrumentTickModel> shownInstrumentTicks;

	ShownInstrumentNoteModel(Instrument instrument, Note note) {
		this.shownInstrumentTicks = note.getTicks().stream()
				.map(tick -> tick.getInstrumentData(instrument))
				.map(ShownInstrumentTickModel::new)
				.toList();
	}

	public List<ShownInstrumentTickModel> getShownInstrumentTicks() {
		return shownInstrumentTicks;
	}

	void destroy() {
		shownInstrumentTicks.forEach(ShownInstrumentTickModel::destroy);
	}
}
