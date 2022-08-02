package com.vaadin.devday.demo.views;

import com.vaadin.flow.router.Route;

@Route("split2")
public class SplitLayoutView2 extends AbstractSplitLayoutView {

	public SplitLayoutView2() {
		placeHolderText = "grid ok, split position wrong";
		fullSize = true;
	}
}
