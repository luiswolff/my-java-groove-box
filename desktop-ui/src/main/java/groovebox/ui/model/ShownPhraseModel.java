package groovebox.ui.model;

import java.util.LinkedHashMap;
import java.util.Map;

import groovebox.service.Instrument;
import groovebox.service.Phrase;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ShownPhraseModel {
	private final ObjectProperty<Map<Instrument, ShownInstrumentRowModel>> shownPhraseModel = new SimpleObjectProperty<>();
	ShownPhraseModel(ObjectProperty<Phrase> phraseProperty) {
		phraseProperty.subscribe(this::onPhraseChanged);
	}

	private void onPhraseChanged(Phrase phrase) {
		Map<Instrument, ShownInstrumentRowModel> shownInstrumentRowModels = shownPhraseModel.get();
		if (shownInstrumentRowModels != null) {
			shownInstrumentRowModels.values().forEach(ShownInstrumentRowModel::destroy);
		}
		shownInstrumentRowModels = new LinkedHashMap<>(Instrument.values().length);
		for (Instrument instrument : Instrument.values()) {
			shownInstrumentRowModels.put(instrument, new ShownInstrumentRowModel(instrument, phrase));
		}
		shownPhraseModel.set(shownInstrumentRowModels);
	}

	public ObjectProperty<Map<Instrument, ShownInstrumentRowModel>> shownPhraseModelProperty() {
		return shownPhraseModel;
	}
}
