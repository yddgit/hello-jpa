package com.my.project.jpa.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

@Route
public class MainView extends VerticalLayout {

	private final CustomerRepository repo;
	private final CustomerEditor editor;
	private final Grid<Customer> grid;
	private final TextField filter;
	private final Button addNewBtn;

	public MainView(CustomerRepository repo, CustomerEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Customer.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New customer", VaadinIcon.PLUS.create());

		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, this.editor);

		grid.setHeight("300px");
		grid.setColumns("id", "firstName", "lastName");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filter by last name");
		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		grid.asSingleSelect().addValueChangeListener(e -> this.editor.editCustomer(e.getValue()));

		addNewBtn.addClickListener(e -> this.editor.editCustomer(new Customer("", "")));

		this.editor.setChangeHandler(() -> {
			this.editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		listCustomers(null);
	}

	private void listCustomers(String filterText) {
		if(StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		} else {
			grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
		}
	}
}