package groovebox.services;

public record TrackData(int resolution, NoteDataBytes[][] noteDataTable, int loopCount, float tempoInBPM) {
}
