package com.vaadin.devday.demo.views;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.componentfactory.Popup;
import com.vaadin.devday.demo.MainLayout;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;

@Route(value = GridView.ROUTE, layout = MainLayout.class)
@PageTitle(GridView.TITLE)
public class GridView extends SplitLayout {
	public static final String ROUTE = "grid";
	public static final String TITLE = "Grid";

    private Grid<MonthlyExpense> expensesGrid;
    private TextField limit;
    private int index = 0;
    private FormLayout form = null;
	NumberField expenseField = new NumberField();
    MonthlyExpense currentExpense;
	private Popup popup;
    
    public GridView() {
    	VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        Label label = new Label(TITLE);
        label.addClassName("title-label");
        content.add(label);
        
        expensesGrid = new Grid<>();
        limit = createLimitTextField();
		HorizontalLayout tools = new HorizontalLayout();
        tools.add(limit);

        HorizontalLayout buttons = createToolButtons(content);

		tools.setWidth("100%");
   		tools.add(buttons);
        content.add(tools);
        
        content.add(expensesGrid);
        content.expand(expensesGrid);

        this.setSizeFull();
        this.setOrientation(Orientation.VERTICAL);
        this.addToPrimary(content);
        form = createForm();
        this.addToSecondary(form);
        this.setSplitterPosition(80);
        initalizeAndPopulateGrid(expensesGrid);
    }

	private HorizontalLayout createToolButtons(VerticalLayout content) {
		Span indexLabel = new Span("2000");

        // Create button and Popup (later populated by column selector)
        Button popButton = new Button("Columns");
        popButton.setId("columnsbutton");
        popup = new Popup();
        popup.setFor("columnsbutton");
        content.add(popup);

        // Buttons to add/decrease the year
        Button upBtn = new Button();
        upBtn.setIcon(new Icon(VaadinIcon.ARROW_UP));
        Button downBtn = new Button();
        downBtn.setIcon(new Icon(VaadinIcon.ARROW_DOWN));
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.getStyle().set("margin-left", "auto");
   		upBtn.addClickListener(event -> {
   			index = index - 12;
   			if (index < 0) index = 0;
        	scrollTo(expensesGrid,index);
        	indexLabel.setText(Integer.toString(2000+(index/12)));
        });
   		downBtn.addClickListener(event -> {
   			index = index + 12;
   			if (index > 12*19) index = 0;
        	scrollTo(expensesGrid,index);
        	indexLabel.setText(Integer.toString(2000+(index/12)));
        });
   		indexLabel.addClickListener(event ->{
        	scrollTo(expensesGrid,index);
   		});
		buttons.add(indexLabel,upBtn,downBtn,popButton);
		return buttons;
	}

	private FormLayout createForm() {
		FormLayout form = new FormLayout();
    	TimePicker timePicker = new TimePicker();
    	TextField nameField = new TextField();
    	DatePicker datePicker = new DatePicker();
		timePicker.setWidth("100%");
		datePicker.setWidth("100%");
		nameField.setWidth("100%");
		form.setSizeFull();
		form.addFormItem(nameField,"Name: ").getElement().setAttribute("colspan", "2");
		form.addFormItem(datePicker,"Birth date: ");
		form.addFormItem(timePicker,"Birth time: ");
		form.addFormItem(expenseField, "Expenses");
		expenseField.setStep(1d);
		expenseField.setHasControls(true);
		expenseField.setSuffixComponent(new Span("EUR"));
		return form;
	}
	
    private TextField createLimitTextField() {
        TextField limit = new TextField("Limit for monthly expenses");
        limit.addClassName("limit-field");
        limit.addValueChangeListener(event -> expensesGrid.getDataProvider().refreshAll());
        limit.getElement().getStyle().set("--limit-field-color", "#2dd7a4");
        return limit;
    }

    public static void scrollTo(Grid<?> grid, int index) {
        ListDataProvider<MonthlyExpense> ldp =  (ListDataProvider) grid.getDataProvider();
        if (ldp.getFilter() == null) UI.getCurrent().getPage().executeJavaScript("$0._scrollToIndex(" + index + ")", grid.getElement());
    }
    
    private String getStyles() {
    	return "background:white";
    }
    
    private void initalizeAndPopulateGrid(Grid<MonthlyExpense> grid) {
    	grid.addClassName("my-grid");
    	grid.addColumn(TemplateRenderer.<MonthlyExpense>of("<div style$=\"[[item.styles]]\">[[item.expenses]]</div>")
    			.withProperty("styles", MonthlyExpense::getStyles)
    			.withProperty("expenses", MonthlyExpense::getYear)
    	).setHeader("Year").setKey("year").setId("year-column");
    	addYearSelectorMenuToColumnHeader(grid);
    	GridContextMenu<MonthlyExpense> menu = new GridContextMenu<>(grid);
    	populateGridContextMenu(grid,menu);    	
//    	menu.getElement().setProperty("selector", "[part~=\"body-cell\"]");
        grid.addColumn(MonthlyExpense::getMonth).setHeader("Month").setKey("month").setId("month-column");
    	addMonthFilterMenuToColumnHeader(grid); 
        NumberField numberField = new NumberField();
        grid.addColumn(MonthlyExpense::getExpenses).setKey("expenses").setHeader("Expenses").setClassNameGenerator(monthlyExpense -> monthlyExpense.getExpenses() >= getMonthlyExpenseLimit() ? "warning-grid-cell" : "green-grid-cell").setEditorComponent(numberField);
        grid.addItemDoubleClickListener(event -> {
        	grid.getEditor().editItem(event.getItem());        	
        });
        Binder<MonthlyExpense> binder = new Binder<>();
        binder.forField(numberField).bind(MonthlyExpense::getExpenses,MonthlyExpense::setExpenses);
        grid.getEditor().setBinder(binder);
		grid.addColumn(new ComponentRenderer<Checkbox,MonthlyExpense>(expense ->  {
			Checkbox check = new Checkbox();
			check.setEnabled(false);			
			grid.addSelectionListener(event -> {
				if (event.getAllSelectedItems().contains(expense)) {
					System.out.println("Selected "+expense.toString());
					check.setValue(true);
				} else {
					check.setValue(false);
				}
			});
			check.addValueChangeListener(event -> {
				if (event.isFromClient()) {
					System.out.println("Check box clicked");
					grid.select(expense);
				}
			});
			return check;
			
		})).setWidth("50px").setKey("select").setHeader("Select");
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        List<MonthlyExpense> data = getData();
        grid.setItems(data);
        grid.addSelectionListener(event -> {
        	event.getFirstSelectedItem().ifPresent(expense -> {
        		expenseField.setValue(expense.getExpenses());
        		currentExpense = expense;
        	});
        });
        expenseField.addValueChangeListener(event -> {
        	if (event.isFromClient() && currentExpense != null) {
        		currentExpense.setExpenses(event.getValue());
        		grid.getDataProvider().refreshItem(currentExpense);
        	}
        });
        //        grid.addItemClickListener(event -> {
//        	getUI().ifPresent(ui -> ui.navigate(MainView.ROUTE+"/scroll"));
//        });

        grid.recalculateColumnWidths();
        
        addColumnSelectorMenu(grid);
        
    }

	private void addColumnSelectorMenu(Grid<MonthlyExpense> grid) {
		// Add column selector to Popup        
        VerticalLayout popDiv = new VerticalLayout();
        for (Column<MonthlyExpense> column : grid.getColumns()) {
        	Checkbox check = new Checkbox(column.getKey());
        	check.getStyle().set("border", "white 1px solid");
        	check.getStyle().set("border-radius", "5px");
        	check.getStyle().set("width", "200px");
        	check.setValue(true);
        	check.addValueChangeListener(event -> {
        		column.setVisible(event.getValue());
        	});
        	popDiv.add(check);
        }
        popup.add(popDiv);
        popup.getElement().executeJs("this.$.popupOverlay.addEventListener('vaadin-overlay-close', () => $0.$server.popupClosed())",getElement());		
	}

	@ClientCallable
	public void popupClosed() {
		System.out.println("Popup closed");
	}
	
	private void populateGridContextMenu(Grid<MonthlyExpense> grid, GridContextMenu<MonthlyExpense> menu) {
   		GridMenuItem<MonthlyExpense> menuItem = menu.addItem("Item", event -> {
   			event.getItem().ifPresent(item -> Notification.show("This is "+item.getYear()+"/"+item.getMonth(),3000,Position.TOP_START).addThemeVariants(NotificationVariant.LUMO_CONTRAST));   			
   		});
	}

	private void addYearSelectorMenuToColumnHeader(Grid<MonthlyExpense> grid) {
    	Div div = new Div();
    	div.setSizeFull();
    	div.add(new Text("Year"));
    	grid.getHeaderRows().get(0).getCell(grid.getColumnByKey("year")).setComponent(div);
    	ContextMenu menu = new ContextMenu(div);
    	populateContextMenu(grid, menu);
	}

	private void addMonthFilterMenuToColumnHeader(Grid<MonthlyExpense> grid) {
    	Div div = new Div();
    	div.setSizeFull();
    	div.add(new Text("Month"));
    	grid.getHeaderRows().get(0).getCell(grid.getColumnByKey("month")).setComponent(div);
    	ContextMenu menu = new ContextMenu(div);
    	populateFilterMenu(grid, menu);
	}

	private void populateFilterMenu(Grid<MonthlyExpense> grid, ContextMenu menu) {
		menu.addItem("All months", event -> {
	        ListDataProvider<MonthlyExpense> ldp =  (ListDataProvider) grid.getDataProvider();
	        ldp.clearFilters();
		});
		String[] months = new java.text.DateFormatSymbols().getMonths();
		for (String month : months) {
    		menu.addItem(month, event -> {
    	        ListDataProvider<MonthlyExpense> ldp =  (ListDataProvider) grid.getDataProvider();
    	        ldp.clearFilters();
    	        ldp.setFilter(item -> item.getMonth().equals(month));
    		});
    	}
	}


	private void populateContextMenu(Grid<MonthlyExpense> grid, ContextMenu menu) {
		for (int i=0;i<10;i++) {
    		final int index = i; 
    		menu.addItem("200"+i, event -> {
    			scrollTo(grid,index*12);
    		});
    	}
    	for (int i=10;i<20;i++) {
    		final int index = i; 
    		menu.addItem("20"+i, event -> {
    			scrollTo(grid,index*12);
    		});
    	}
	}
	
	private List<MonthlyExpense> getData() {
		String[] monthNames = new java.text.DateFormatSymbols().getMonths();
        List<MonthlyExpense> data = new ArrayList<>();
        for (int year = 2000; year < 2020; year++ ) {
        	for (int month = 0; month < 12; month++) {
            	data.add(new MonthlyExpense(monthNames[month], year, getExpenses()));
        	}
    	}
		return data;
	}

    // Randomize a value between 300 and 800
    private Double getExpenses() {
        return Math.floor((Math.random() * 1000) % 500 + 300);
    }

    private int getMonthlyExpenseLimit() {
        if (limit.getValue() == null || limit.getValue().isEmpty()) {
            return 100000;
        }
        return Integer.parseInt(limit.getValue());
    }
    
}
