package com.vaadin.devday.demo.views;

import com.vaadin.flow.router.Route;

@Route(value = "split4", layout = SplitAppLayout.class)
public class SplitLayoutView4 extends AbstractSplitLayoutView {

	public SplitLayoutView4() {
		placeHolderText = "scrollbar visible; there must be something relevant in the app layout";
		hsplithSecondaryFullHight = true;
		fullSize = true;
	}
}
