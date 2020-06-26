import javax.swing.JFrame;

public class GameScreen {
	
	private static JFrame frame;

	public static void main(String[] args) {
		
		GamePlay gameplay = new GamePlay();
		frame = new JFrame();
		frame.setBounds(10, 10, 700, 600);
		frame.setTitle("Breakout");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(gameplay);
		



	}

}
