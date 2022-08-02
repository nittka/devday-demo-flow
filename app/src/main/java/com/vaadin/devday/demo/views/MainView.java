package com.vaadin.devday.demo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.Theme;

@Route(value = "")
@RouteAlias(value = "myapp")
@Theme(themeFolder = "exampleTheme")
public class MainView extends VerticalLayout {

	public MainView() {
		add(new Button("button with primaryColor"));
	}

}