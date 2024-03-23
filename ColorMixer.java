import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import net.miginfocom.swing.MigLayout;

public class ColorMixer {

	public static void addColor(JPanel colorPanel, JButton removeColorsValidate) {
		JButton color = new JButton("[цвет]");
		color.setToolTipText("Нажмите, чтобы выбрать цвет");
		color.setVisible(true);
		color.setBackground(Color.BLACK);
		color.setForeground(Color.WHITE);
		color.addActionListener(e -> {
			Color selectedColor = JColorChooser.showDialog(null,"Выбор цвета",Color.WHITE);
			if (selectedColor == null) {
				color.setBackground(color.getBackground());
			} else {
				color.setBackground(selectedColor);
			}
		});
		colorPanel.add(color);
		colorPanel.revalidate();
		removeColorsValidate.setEnabled(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame colorMixerWindow = new JFrame("ColorMixer");
			colorMixerWindow.setSize(500,350);
			colorMixerWindow.setVisible(true);
			colorMixerWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			JPanel main = new JPanel();
			main.setLayout(new MigLayout());

			JPanel colors = new JPanel();
			colors.setLayout(new BoxLayout(colors,BoxLayout.X_AXIS));
			main.add(colors,"wrap");

			JPanel control = new JPanel();
			control.setLayout(new BoxLayout(control,BoxLayout.X_AXIS));

			JButton addColors = new JButton("Добавить цвет");
			control.add(addColors);

			JButton removeLastColors = new JButton("Удалить последний цвет");
			removeLastColors.setEnabled(false);
			removeLastColors.addActionListener(e -> {
				List<Component> comps = new ArrayList<>();
				for (Component c : colors.getComponents()) {
					comps.add(c);
				}
				colors.remove(comps.getLast());
				removeLastColors.revalidate();
				if (colors.getComponents().length == 0) {
					removeLastColors.setEnabled(false);
				}
			});

			addColors.addActionListener(e -> {
				addColor(colors,removeLastColors);
			});

			control.add(removeLastColors);

			JButton mixColors = new JButton("Смешать!");

			mixColors.setForeground(Color.WHITE);
			mixColors.setBackground(new Color(0,128,0));

			control.add(mixColors);

			main.add(control,"wrap");

			JPanel resultColor = new JPanel();
			resultColor.setMinimumSize(new Dimension(250,100));
			resultColor.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			main.add(resultColor,"wrap");

			JLabel finalColor = new JLabel();
			finalColor.setFont(new Font("Arial",Font.PLAIN,18));
			main.add(finalColor);

			colorMixerWindow.add(new JScrollPane(main));

			mixColors.addActionListener(e -> {
				List<Color> colorsList = new ArrayList<>();
				for (Component buttonWithColor : colors.getComponents()) {
					colorsList.add(buttonWithColor.getBackground());
				}
				int R = 0;
				int G = 0;
				int B = 0;
				String HR; //HEX
				String HG; //HEX
				String HB; //HEX
				for (Color color : colorsList) {
					R += color.getRed();
				}
				R /= colorsList.size();
				for (Color color : colorsList) {
					G += color.getGreen();
				}
				G /= colorsList.size();
				for (Color color : colorsList) {
					B += color.getBlue();
				}
				B /= colorsList.size();
				HR = Integer.toHexString(R);
				HG = Integer.toHexString(G);
				HB = Integer.toHexString(B);
				if (HR.length() < 2) {
					HR = "0" + HR;
				}
				if (HG.length() < 2) {
					HG = "0" + HG;
				}
				if (HB.length() < 2) {
					HB = "0" + HB;
				}
				resultColor.setBackground(new Color(R,G,B));
				finalColor.setText(String.format("%d, %d, %d = #%s%s%s",R,G,B,HR,HG,HB));
			});
		});
	}   
}