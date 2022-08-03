package com.vaadin.devday.demo.views;

import com.vaadin.flow.router.Route;

@Route(value = "split4", layout = SplitAppLayout.class)
public class SplitLayoutView4 extends AbstractSplitLayoutView {

	public SplitLayoutView4() {
		placeHolderText = "OK!!, unset flex for filter, no full height for 'grid'";
		fullSize = true;
		unsetFlex = true;
	}
}
