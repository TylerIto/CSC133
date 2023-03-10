package com.mycompany.a1;

import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import java.lang.String;

public class Game extends Form{
	private GameWorld gw;
	
	public Game() {
		gw = new GameWorld();
		gw.init();
		play();
	}
	
	private void play() {
		Label myLabel = new Label("Enter a Command:");
		this.addComponent(myLabel);
		final TextField myTextField = new TextField();
		this.addComponent(myTextField);
		this.show();
		
		myTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String sCommand = myTextField.getText().toString();
				myTextField.clear();
				if(sCommand.length() !=0)
					switch (sCommand.charAt(0)) {
					case 'a':
						gw.accelerate();
						break;
					case 'b':
						gw.brake();
						break;
					case 'l':
						gw.headingLeft();
						break;
					case 'r':
						gw.headingRight();
						break;
					case '1':
						gw.collisionFlag(1);
						break;
					case '2':
						gw.collisionFlag(2);
						break;
					case '3':
						gw.collisionFlag(3);
						break;
					case '4':
						gw.collisionFlag(4);						
						break;
					case '5':
						gw.collisionFlag(5);
						break;
					case '6':
						gw.collisionFlag(6);
						break;
					case '7':
						gw.collisionFlag(7);
						break;
					case '8':
						gw.collisionFlag(8);
						break;
					case '9':
						gw.collisionFlag(9);
						break;
					case 'f':
						gw.collisionFood();
						break;
					case 'g':
						gw.collisionSpider();
						break;
					case 't':
						gw.tick();
						break;
					case 'd':
						gw.display();
						break;
					case 'm':
						gw.map();
						break;
					case 'x':
						gw.exit();
						break;
					default:
						System.out.println("Unknown Command");
						break;
					// add code to handle rest of the commands
					} // switch
			} // actionPerformed
		} //new ActionListener()
		); // addActionListener
	} // play
}