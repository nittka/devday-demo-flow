package com.vaadin.devday.demo.views;

import com.vaadin.flow.router.Route;

@Route("split1")
public class SplitLayoutView1 extends AbstractSplitLayoutView {

	public SplitLayoutView1() {
		placeHolderText = "filter ok, grid too small";
		//full height of secondary component is irrelevant
		//		hsplithSecondaryFullHight = true;
	}
}
