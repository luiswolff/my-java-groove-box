package groovebox.ui.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import groovebox.service.Instrument;
import groovebox.service.Phrase;
import groovebox.ui.InstrumentTickPositionEnum;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class ShownPhraseModel {
	private final ObjectProperty<Map<Instrument, ShownInstrumentRowModel>> shownPhraseModel = new SimpleObjectProperty<>();

	private final MapProperty<ShownPhraseRow, List<ShownInstrumentTickModel>> phraseGridCells = new SimpleMapProperty<>(FXCollections.observableMap(new LinkedHashMap<>()));

	ShownPhraseModel(ObjectProperty<Phrase> phraseProperty) {
		phraseProperty.subscribe(this::onPhraseChanged);
	}
	private void onPhraseChanged(Phrase phrase) {
		// clear old
		Map<Instrument, ShownInstrumentRowModel> shownInstrumentRowModels = shownPhraseModel.get();
		if (shownInstrumentRowModels != null) {
			shownInstrumentRowModels.values().forEach(ShownInstrumentRowModel::destroy);
		}
		//clear new
		phraseGridCells.values().stream().flatMap(List::stream).forEach(ShownInstrumentTickModel::destroy);

		// refill old
		shownInstrumentRowModels = new LinkedHashMap<>(Instrument.values().length);
		for (int i = 0; i < Instrument.values().length; i++) {
			Instrument instrument = Instrument.values()[i];
			shownInstrumentRowModels.put(instrument, new ShownInstrumentRowModel(instrument, phrase, getPosition(Instrument.values(), i)));
		}
		shownPhraseModel.set(shownInstrumentRowModels);

		// refill new
		Map<ShownPhraseRow, List<ShownInstrumentTickModel>> phraseGridCells = new  LinkedHashMap<>(Instrument.values().length);
		for (int i = 0; i < Instrument.values().length; i++) {
			Instrument instrument = Instrument.values()[i];
			ShownInstrumentRowModel row = shownPhraseModel.getValue().get(instrument);
			phraseGridCells.put(new ShownPhraseRow(i, instrument.name()), row.getShownNotes().stream()
					.flatMap(note -> note.getShownInstrumentTicks().stream())
					.toList());

		}
		this.phraseGridCells.set(FXCollections.observableMap(phraseGridCells));
	}

	private InstrumentTickPositionEnum getPosition(Instrument[] instruments, int row) {
		if (row == 0) {
			return InstrumentTickPositionEnum.TOP;
		} else if (row == instruments.length - 1) {
			return InstrumentTickPositionEnum.BOTTOM;
		}
		return InstrumentTickPositionEnum.CENTER;
	}

	public MapProperty<ShownPhraseRow, List<ShownInstrumentTickModel>> phraseGridCellsProperty() {
		return phraseGridCells;
	}
}
