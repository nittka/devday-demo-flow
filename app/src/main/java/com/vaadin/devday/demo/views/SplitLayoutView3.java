package com.vaadin.devday.demo.views;

import com.vaadin.flow.router.Route;

@Route("split3")
public class SplitLayoutView3 extends AbstractSplitLayoutView {

	public SplitLayoutView3() {
		placeHolderText = "scrollbar visible";
		hsplithSecondaryFullHight = true;
		fullSize = true;
	}
}
