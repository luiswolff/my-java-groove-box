package groovebox.ui.model;

import java.util.ArrayList;
import java.util.List;

import groovebox.service.Instrument;
import groovebox.service.InstrumentDataApi;
import groovebox.service.Note;
import groovebox.ui.InstrumentTickPositionEnum;

public class ShownInstrumentNoteModel {
	private final List<ShownInstrumentTickModel> shownInstrumentTicks;

	ShownInstrumentNoteModel(Instrument instrument, Note note, InstrumentTickPositionEnum position) {
		List<ShownInstrumentTickModel> shownInstrumentTicks = new ArrayList<>(note.getTicks().size());
		for (int i = 0; i < note.getTicks().size(); i++) {
			InstrumentDataApi tick = note.getTicks().get(i).getInstrumentData(instrument);
			shownInstrumentTicks.add(new ShownInstrumentTickModel(i == 0, position, tick));
		}
		this.shownInstrumentTicks = shownInstrumentTicks;
	}

	public List<ShownInstrumentTickModel> getShownInstrumentTicks() {
		return shownInstrumentTicks;
	}

	void destroy() {
		shownInstrumentTicks.forEach(ShownInstrumentTickModel::destroy);
	}
}
