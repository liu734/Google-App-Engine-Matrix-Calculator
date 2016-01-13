package com.example.matrix_calculator.client;

import com.example.matrix_calculator.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Matrix_calculator implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// button
		final Button LoginButton = new Button("Login");
		final Button RegisterButton = new Button("Register");
		final Button submitButton = new Button("Submit");
		final Button resetButton = new Button("Reset");

		//
		final Button evaluateButton = new Button("Evaluate");

		// textbox in login

		final TextBox nameField = new TextBox();
		final TextBox nameField2 = new TextBox();

		// textbox in register
		final TextBox nameField3 = new TextBox();
		final TextBox nameField4 = new TextBox();
		final TextBox nameField5 = new TextBox();
		final TextBox nameField6 = new TextBox();
		final TextBox nameField7 = new TextBox();
		// in calculator

		final TextArea nameField8 = new TextArea();
		final TextArea nameField9 = new TextArea();
		final TextArea nameField10 = new TextArea();
		
		final ListBox dropDownMenu = new ListBox();
		
		dropDownMenu.addItem("A*B");
		dropDownMenu.addItem("B*A");
		dropDownMenu.addItem("A+B");
		dropDownMenu.addItem("A-B");
		dropDownMenu.addItem("A-inverse");
		dropDownMenu.addItem("B-inverse");
		
		

		nameField.setText("username");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		LoginButton.addStyleName("sendButton");
		RegisterButton.addStyleName("sendButton");
		submitButton.addStyleName("sendButton");
		resetButton.addStyleName("sendButton");
		LoginButton.addStyleName("sendButton");

		evaluateButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("nameFieldContainer2").add(nameField2);

		// register
		RootPanel.get("nameFieldContainer3").add(nameField3);
		RootPanel.get("nameFieldContainer4").add(nameField4);
		RootPanel.get("nameFieldContainer5").add(nameField5);
		RootPanel.get("nameFieldContainer6").add(nameField6);
		RootPanel.get("nameFieldContainer7").add(nameField7);

		// Calculator

		RootPanel.get("nameFieldContainer8").add(nameField8);
		RootPanel.get("nameFieldContainer9").add(nameField9);
		RootPanel.get("nameFieldContainer10").add(nameField10);

		// login button

		RootPanel.get("sendButtonContainer").add(LoginButton);
		RootPanel.get("sendButtonContainer2").add(RegisterButton);

		// register button

		RootPanel.get("sendButtonContainer3").add(submitButton);
		RootPanel.get("sendButtonContainer4").add(resetButton);
		RootPanel.get("sendButtonContainer5").add(evaluateButton);
		
		RootPanel.get("dropDownMenu").add(dropDownMenu);

		// RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();
		

		

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				LoginButton.setEnabled(true);
				LoginButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();

			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 * 
			 * used for login
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = "login\n" + nameField.getText() + "\n"
						+ nameField2.getText();

				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				LoginButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}

		}

		class MyHandler2 implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				registertoServer();

			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					registertoServer();
				}
			}

			private void registertoServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String textToServer = "register " + nameField3.getText();
				textToServer += (" " + nameField4.getText());
				textToServer += (" " + nameField5.getText());
				textToServer += (" " + nameField6.getText());
				textToServer += (" " + nameField7.getText());

				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				LoginButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {

							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}

		}

		class MyHandler3 implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				registertoServer();

			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					registertoServer();
				}
			}

			private void registertoServer() {
				// First, we validate the input.
				errorLabel.setText("");
				
				
				String ma = nameField8.getText();
				String mb = nameField9.getText();

	
				 Matrix m=new Matrix(ma);
				 Matrix n=new Matrix(mb);
				 
				 
				 System.out.println(dropDownMenu.getTitle());
				 if(dropDownMenu.getTitle().equals("A-B")){		
					 Matrix n1=m.minus(n); //minus
					 nameField10.setText(n1.toString());
				 }
				 else if(dropDownMenu.getTitle().equals("A+B")){
				 
					 Matrix n1=m.add(n); //addition
					 nameField10.setText(n1.toString());
				 }
				 else if(dropDownMenu.getTitle().equals("A*B")){
					 Matrix n1=m.multiply(n); // multiplication
					 nameField10.setText(n1.toString());
				 }
				 else if(dropDownMenu.getTitle().equals("A-inverse")){
					 Matrix n1=m.rev(m); //reverse;
					 nameField10.setText(n1.toString());
				 }
				 else if(dropDownMenu.getTitle().equals("B-inverse")){
					 Matrix n1 = n.rev(n);
					 nameField10.setText(n1.toString());
				 }
				// Matrix n5=m.turn(m); //Transpose

				 
				LoginButton.setEnabled(false);

			}

		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		LoginButton.addClickHandler(handler);

		MyHandler2 handler2 = new MyHandler2();
		submitButton.addClickHandler(handler2);

		MyHandler3 handler3 = new MyHandler3();
		evaluateButton.addClickHandler(handler3);

		nameField.addKeyUpHandler(handler);
		nameField2.addKeyUpHandler(handler);
		nameField3.addKeyUpHandler(handler2);
		nameField4.addKeyUpHandler(handler2);
		nameField5.addKeyUpHandler(handler2);
		nameField6.addKeyUpHandler(handler2);
		nameField7.addKeyUpHandler(handler2);
		nameField8.addKeyUpHandler(handler3);
		nameField9.addKeyUpHandler(handler3);
		nameField10.addKeyUpHandler(handler3);
		dropDownMenu.addKeyUpHandler(handler3);
	}
}
