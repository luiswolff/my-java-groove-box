package groovebox.adapter;

public record TrackData(int resolution, NoteDataBytes[][] noteDataTable, int loopCount, float tempoInBPM) {
}
