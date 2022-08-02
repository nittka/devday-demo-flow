package com.vaadin.devday.demo.views;

import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexWrap;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

public class AbstractSplitLayoutView extends SplitLayout {

	protected boolean hsplithSecondaryFullHight = false;
	protected boolean fullSize = false;
	protected String placeHolderText = "Placeholder";

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		setOrientation(Orientation.VERTICAL);
		addToPrimary(createFilterLayout());
		setPrimaryStyle("flex", "unset");
		SplitLayout splitLayout = new SplitLayout();
		if (hsplithSecondaryFullHight) {
			splitLayout.setSizeFull();
		}
		splitLayout.setOrientation(Orientation.HORIZONTAL);
		splitLayout.addToPrimary(createGridLayout());
		splitLayout.addToSecondary(createPlaceholder());
		addToSecondary(splitLayout);
		if (fullSize) {
			setSizeFull();
		}
	}

	private VerticalLayout createPlaceholder() {
		VerticalLayout layout = new VerticalLayout();
		layout.setWidth("400px");
		layout.add(new Span(placeHolderText));
		return layout;
	}

	private FlexLayout createFilterLayout() {
		FlexLayout layout = new FlexLayout();
		for (int i = 0; i < 15; i++) {
			TextField filter = new TextField("Filter " + i);
			filter.addThemeVariants(TextFieldVariant.LUMO_SMALL);
			filter.setHeight("min-content");
			layout.add(filter);
		}
		layout.setFlexWrap(FlexWrap.WRAP);
		return layout;
	}

	private Component createGridLayout() {
		VerticalLayout layout = new VerticalLayout();

		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.setHeight("60px");
		toolBar.add(new Span("Grid"));

		Grid<String> grid = new Grid<>();
		final List<String> items = Arrays.asList("Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight",
			"Nine", "Ten");
		Column<String> col1 = grid.addColumn(value -> items.indexOf(value)).setHeader("Index");
		Column<String> col2 = grid.addColumn(value -> value.toString()).setHeader("A number");
		HeaderRow row = grid.appendHeaderRow();
		Div div1 = new Div();
		div1.setText("0");
		div1.getElement().getStyle().set("text-align", "left");
		div1.getElement().getStyle().set("width", "100%");
		Div div2 = new Div();
		div2.setText("some Text");
		div2.getElement().getStyle().set("text-align", "left");
		div2.getElement().getStyle().set("width", "100%");
		row.getCell(col1).setComponent(div1);
		row.getCell(col2).setComponent(div2);
		grid.addSelectionListener(event -> {
			event.getFirstSelectedItem().ifPresent(item -> {
				div1.setText("" + items.indexOf(item));
				div2.setText(item.toString());
			});
		});

		layout.add(toolBar, grid);
		layout.setFlexGrow(1, grid);
		return layout;
	}
}
