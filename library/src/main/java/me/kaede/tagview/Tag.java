package me.kaede.tagview;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * Tag Entity
 */
public class Tag {

	public int     id;
	public String  text;
	public int     tagTextColor;
	public float   tagTextSize;
	public int     layoutColor;
	public int     layoutColorPress;
	public boolean isDeletable;
	public int     deleteIndicatorColor;
	public float   deleteIndicatorSize;
	public float   radius;
	public String  deleteIcon;
	public float   layoutBorderSize;
	public int     layoutBorderColor;
	public Drawable background;
	public boolean isSelect = false;
	public boolean clickable = true;

	public boolean isClickable() {
		return clickable;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

	public Tag(String text) {
		init(0, text, Constants.DEFAULT_TAG_TEXT_COLOR, Constants.DEFAULT_TAG_TEXT_SIZE, Constants.DEFAULT_TAG_LAYOUT_COLOR, Constants.DEFAULT_TAG_LAYOUT_COLOR_PRESS,
				Constants.DEFAULT_TAG_IS_DELETABLE, Constants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, Constants.DEFAULT_TAG_DELETE_INDICATOR_SIZE, Constants.DEFAULT_TAG_RADIUS, Constants.DEFAULT_TAG_DELETE_ICON, Constants.DEFAULT_TAG_LAYOUT_BORDER_SIZE, Constants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);
	}

	public Tag(String text, int color) {
		init(0, text, Constants.DEFAULT_TAG_TEXT_COLOR, Constants.DEFAULT_TAG_TEXT_SIZE, color, Constants.DEFAULT_TAG_LAYOUT_COLOR_PRESS, Constants.DEFAULT_TAG_IS_DELETABLE,
				Constants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, Constants.DEFAULT_TAG_DELETE_INDICATOR_SIZE, Constants.DEFAULT_TAG_RADIUS, Constants.DEFAULT_TAG_DELETE_ICON, Constants.DEFAULT_TAG_LAYOUT_BORDER_SIZE, Constants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);

	}

	public Tag(String text, String color) {
		init(0, text, Constants.DEFAULT_TAG_TEXT_COLOR, Constants.DEFAULT_TAG_TEXT_SIZE, Color.parseColor(color), Constants.DEFAULT_TAG_LAYOUT_COLOR_PRESS,
				Constants.DEFAULT_TAG_IS_DELETABLE, Constants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, Constants.DEFAULT_TAG_DELETE_INDICATOR_SIZE, Constants.DEFAULT_TAG_RADIUS, Constants.DEFAULT_TAG_DELETE_ICON, Constants.DEFAULT_TAG_LAYOUT_BORDER_SIZE, Constants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);

	}

	public Tag(int id, String text) {
		init(id, text, Constants.DEFAULT_TAG_TEXT_COLOR, Constants.DEFAULT_TAG_TEXT_SIZE, Constants.DEFAULT_TAG_LAYOUT_COLOR, Constants.DEFAULT_TAG_LAYOUT_COLOR_PRESS,
				Constants.DEFAULT_TAG_IS_DELETABLE, Constants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, Constants.DEFAULT_TAG_DELETE_INDICATOR_SIZE, Constants.DEFAULT_TAG_RADIUS, Constants.DEFAULT_TAG_DELETE_ICON, Constants.DEFAULT_TAG_LAYOUT_BORDER_SIZE, Constants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);
	}

	public Tag(int id, String text, int color) {
		init(id, text, Constants.DEFAULT_TAG_TEXT_COLOR, Constants.DEFAULT_TAG_TEXT_SIZE, color, Constants.DEFAULT_TAG_LAYOUT_COLOR_PRESS, Constants.DEFAULT_TAG_IS_DELETABLE,
				Constants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, Constants.DEFAULT_TAG_DELETE_INDICATOR_SIZE, Constants.DEFAULT_TAG_RADIUS, Constants.DEFAULT_TAG_DELETE_ICON, Constants.DEFAULT_TAG_LAYOUT_BORDER_SIZE, Constants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);

	}

	public Tag(int id, String text, String color) {
		init(id, text, Constants.DEFAULT_TAG_TEXT_COLOR, Constants.DEFAULT_TAG_TEXT_SIZE, Color.parseColor(color), Constants.DEFAULT_TAG_LAYOUT_COLOR_PRESS,
				Constants.DEFAULT_TAG_IS_DELETABLE, Constants.DEFAULT_TAG_DELETE_INDICATOR_COLOR, Constants.DEFAULT_TAG_DELETE_INDICATOR_SIZE, Constants.DEFAULT_TAG_RADIUS, Constants.DEFAULT_TAG_DELETE_ICON, Constants.DEFAULT_TAG_LAYOUT_BORDER_SIZE, Constants.DEFAULT_TAG_LAYOUT_BORDER_COLOR);

	}

	private void init(int id, String text, int tagTextColor, float tagTextSize, int layout_color, int layout_color_press, boolean isDeletable, int deleteIndicatorColor,
	                  float deleteIndicatorSize, float radius, String deleteIcon, float layoutBorderSize, int layoutBorderColor) {
		this.id = id;
		this.text = text;
		this.tagTextColor = tagTextColor;
		this.tagTextSize = tagTextSize;
		this.layoutColor = layout_color;
		this.layoutColorPress = layout_color_press;
		this.isDeletable = isDeletable;
		this.deleteIndicatorColor = deleteIndicatorColor;
		this.deleteIndicatorSize = deleteIndicatorSize;
		this.radius = radius;
		this.deleteIcon = deleteIcon;
		this.layoutBorderSize = layoutBorderSize;
		this.layoutBorderColor = layoutBorderColor;
	}
	public Drawable getBackground() {
		return background;
	}

	public void setBackground(Drawable background) {
		this.background = background;
	}

	public String getDeleteIcon() {
		return deleteIcon;
	}

	public void setDeleteIcon(String deleteIcon) {
		this.deleteIcon = deleteIcon;
	}

	public int getDeleteIndicatorColor() {
		return deleteIndicatorColor;
	}

	public void setDeleteIndicatorColor(int deleteIndicatorColor) {
		this.deleteIndicatorColor = deleteIndicatorColor;
	}

	public float getDeleteIndicatorSize() {
		return deleteIndicatorSize;
	}

	public void setDeleteIndicatorSize(float deleteIndicatorSize) {
		this.deleteIndicatorSize = deleteIndicatorSize;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isDeletable() {
		return isDeletable;
	}

	public void setIsDeletable(boolean isDeletable) {
		this.isDeletable = isDeletable;
	}

	public int getLayoutBorderColor() {
		return layoutBorderColor;
	}

	public void setLayoutBorderColor(int layoutBorderColor) {
		this.layoutBorderColor = layoutBorderColor;
	}

	public float getLayoutBorderSize() {
		return layoutBorderSize;
	}

	public void setLayoutBorderSize(float layoutBorderSize) {
		this.layoutBorderSize = layoutBorderSize;
	}

	public int getLayoutColor() {
		return layoutColor;
	}

	public void setLayoutColor(int layoutColor) {
		this.layoutColor = layoutColor;
	}

	public int getLayoutColorPress() {
		return layoutColorPress;
	}

	public void setLayoutColorPress(int layoutColorPress) {
		this.layoutColorPress = layoutColorPress;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public int getTagTextColor() {
		return tagTextColor;
	}

	public void setTagTextColor(int tagTextColor) {
		this.tagTextColor = tagTextColor;
	}

	public float getTagTextSize() {
		return tagTextSize;
	}

	public void setTagTextSize(float tagTextSize) {
		this.tagTextSize = tagTextSize;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void tagSelect() {
		isSelect = !isSelect;
		if (isSelect) {
			this.tagTextColor = Color.parseColor("#ffffff");
			this.layoutColor = Color.parseColor("#00BFFF");
		} else {
			this.tagTextColor = Constants.DEFAULT_TAG_TEXT_COLOR;
			this.layoutColor = Constants.DEFAULT_TAG_LAYOUT_COLOR;
		}
	}

}
