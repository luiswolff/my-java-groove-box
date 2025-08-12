package groovebox.ui;

enum InstrumentTickPositionEnum {
	TOP("-fx-border-width: 2px 2px 0 2px;"),
	CENTER("-fx-border-width: 0 2px 0 2px;"),
	BOTTOM("-fx-border-width: 0 2px 2px 2px;"),
	;
	private final String borderStyle;

	InstrumentTickPositionEnum(String borderStyle) {
		this.borderStyle = borderStyle;
	}

	public String getBorderStyle() {
		return borderStyle;
	}
}
