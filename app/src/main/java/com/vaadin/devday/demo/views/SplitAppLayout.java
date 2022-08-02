package com.vaadin.devday.demo.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;

public class SplitAppLayout extends AppLayout {

	public SplitAppLayout() {
		addToNavbar(new Button("Button"));
	}
}
