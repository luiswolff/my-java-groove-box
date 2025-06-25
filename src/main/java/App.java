import java.util.List;

public class App {
	public static void main(String[] args) throws Exception {
		SoundService soundService = new SoundService();
		soundService.play(new Beat(List.of(0, 3, 4, 6, 8, 11, 12, 14)));
		Runtime.getRuntime().addShutdownHook(new Thread(soundService::stop));
	}
}
