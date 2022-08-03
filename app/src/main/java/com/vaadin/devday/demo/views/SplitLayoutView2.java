package com.vaadin.devday.demo.views;

import com.vaadin.flow.router.Route;

@Route("split2")
public class SplitLayoutView2 extends AbstractSplitLayoutView {

	public SplitLayoutView2() {
		placeHolderText = "OK!!, unset flex for filter, no full height for 'grid'";
		fullSize = true;
		unsetFlex = true;
	}
}
