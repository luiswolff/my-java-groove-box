package groovebox.services;

public record NoteDataBytes(int firstDataByte, int secondDataByte) {
	public static NoteDataBytes empty() {
		return new NoteDataBytes(0, 0);
	}
}
