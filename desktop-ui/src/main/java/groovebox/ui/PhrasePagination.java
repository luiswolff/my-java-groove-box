package groovebox.ui;

import javafx.scene.control.Pagination;

public class PhrasePagination extends Pagination {
	void apply(GrooveBoxModel model) {
		currentPageIndexProperty().bindBidirectional(model.phraseIndexProperty());
		pageCountProperty().bind(model.phrasesProperty().sizeProperty());
	}
}
