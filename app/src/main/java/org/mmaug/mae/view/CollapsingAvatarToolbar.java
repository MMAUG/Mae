package org.mmaug.mae.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.mmaug.mae.R;

/**
 * Created by poepoe on 20/9/15.
 */
public class CollapsingAvatarToolbar extends LinearLayout
    implements AppBarLayout.OnOffsetChangedListener {

  private View avatarView;
  private TextView titleView;

  private float collapsedPadding;
  private float expandedPadding;

  private float expandedImageSize;
  private float collapsedImageSize;

  private float collapsedTextSize;
  private float expandedTextSize;

  private boolean valuesCalculatedAlready = false;
  private Toolbar toolbar;
  private AppBarLayout appBarLayout;
  private float collapsedHeight;
  private float expandedHeight;
  private float maxOffset;

  private CollapseChangedListener collapseChangedListener;

  public CollapsingAvatarToolbar(Context context) {
    this(context, null);
    init();
  }

  public CollapsingAvatarToolbar(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();

    TypedArray a =
        context.getTheme().obtainStyledAttributes(attrs, R.styleable.CollapsingAvatarToolbar, 0, 0);

    try {
      collapsedPadding = a.getDimension(R.styleable.CollapsingAvatarToolbar_collapsedPadding, -1);
      expandedPadding = a.getDimension(R.styleable.CollapsingAvatarToolbar_expandedPadding, -1);

      collapsedImageSize =
          a.getDimension(R.styleable.CollapsingAvatarToolbar_collapsedImageSize, -1);
      expandedImageSize = a.getDimension(R.styleable.CollapsingAvatarToolbar_expandedImageSize, -1);

      collapsedTextSize = a.getDimension(R.styleable.CollapsingAvatarToolbar_collapsedTextSize, -1);
      expandedTextSize = a.getDimension(R.styleable.CollapsingAvatarToolbar_expandedTextSize, -1);
    } finally {
      a.recycle();
    }

    final Resources resources = getResources();
    if (collapsedPadding < 0) {
      collapsedPadding = resources.getDimension(R.dimen.default_collapsed_padding);
    }
    if (expandedPadding < 0) {
      expandedPadding = resources.getDimension(R.dimen.default_expanded_padding);
    }
    if (collapsedImageSize < 0) {
      collapsedImageSize = resources.getDimension(R.dimen.default_collapsed_image_size);
    }
    if (expandedImageSize < 0) {
      expandedImageSize = resources.getDimension(R.dimen.default_expanded_image_size);
    }
    if (collapsedTextSize < 0) {
      collapsedTextSize = resources.getDimension(R.dimen.default_collapsed_text_size);
    }
    if (expandedTextSize < 0) {
      expandedTextSize = resources.getDimension(R.dimen.default_expanded_text_size);
    }
  }

  public void setCollapseChangedListener(CollapseChangedListener collapseChangedListener) {
    this.collapseChangedListener = collapseChangedListener;
  }

  private void init() {
    setOrientation(HORIZONTAL);
  }

  @NonNull private AppBarLayout findParentAppBarLayout() {
    ViewParent parent = this.getParent();
    if (parent instanceof AppBarLayout) {
      return ((AppBarLayout) parent);
    } else if (parent.getParent() instanceof AppBarLayout) {
      return ((AppBarLayout) parent.getParent());
    } else {
      throw new IllegalStateException(
          "Must be inside an AppBarLayout"); //actually, a collapsingtoolbar
    }
  }

  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    findViews();
    if (!isInEditMode()) {
      appBarLayout.addOnOffsetChangedListener(this);
    } else {
      setExpandedValuesForEditMode();
    }
  }

  private void setExpandedValuesForEditMode() {
    calculateValues();
    updateViews(1f, 0);
  }

  private void findViews() {
    appBarLayout = findParentAppBarLayout();
    toolbar = findSiblingToolbar();
    avatarView = findAvatar();
    titleView = findTitle();
  }

  @NonNull private View findAvatar() {
    View avatar = this.findViewById(R.id.candidate_avatar);
    if (avatar == null) {
      throw new IllegalStateException("View with id ta_avatar not found");
    }
    return avatar;
  }

  @NonNull private TextView findTitle() {
    TextView title = (TextView) this.findViewById(R.id.candidate_name);
    if (title == null) {
      throw new IllegalStateException("TextView with id ta_title not found");
    }
    return title;
  }

  @NonNull private Toolbar findSiblingToolbar() {
    ViewGroup parent = ((ViewGroup) this.getParent());
    for (int i = 0, c = parent.getChildCount(); i < c; i++) {
      View child = parent.getChildAt(i);
      if (child instanceof Toolbar) {
        return (Toolbar) child;
      }
    }
    throw new IllegalStateException("No toolbar found as sibling");
  }

  @Override public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
    if (!valuesCalculatedAlready) {
      calculateValues();
      valuesCalculatedAlready = true;
    }
    float collapsedProgress = -offset / maxOffset;
    updateViews(collapsedProgress, offset);
    notifyListener(collapsedProgress);
  }

  private void calculateValues() {
    collapsedHeight = toolbar.getHeight();
    expandedHeight = appBarLayout.getHeight() - toolbar.getHeight();
    maxOffset = expandedHeight;
  }

  private void updateViews(float collapsedProgress, int currentOffset) {
    float expandedProgress = 1 - collapsedProgress;
    float translation = -currentOffset + ((float) toolbar.getHeight() * expandedProgress);

    float currHeight = collapsedHeight + (expandedHeight - collapsedHeight) * expandedProgress;
    float currentPadding =
        expandedPadding + (collapsedPadding - expandedPadding) * collapsedProgress;
    float currentImageSize =
        collapsedImageSize + (expandedImageSize - collapsedImageSize) * expandedProgress;
    float currentTextSize =
        collapsedTextSize + (expandedTextSize - collapsedTextSize) * expandedProgress;

    setContainerOffset(translation);
    setContainerHeight((int) currHeight);
    setPadding((int) currentPadding);
    setAvatarSize((int) currentImageSize);
    setTextSize(currentTextSize);
  }

  private void setContainerOffset(float translation) {
    this.setTranslationY(translation);
  }

  private void setContainerHeight(int currHeight) {
    this.getLayoutParams().height = currHeight;
  }

  private void setPadding(int currentPadding) {
    this.setPadding(currentPadding, 0, 0, 0);
  }

  private void setTextSize(float currentTextSize) {
    titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, currentTextSize);
  }

  private void setAvatarSize(int currentImageSize) {
    avatarView.getLayoutParams().height = currentImageSize;
    avatarView.getLayoutParams().width = currentImageSize;
  }

  private void notifyListener(float collapsedProgress) {
    if (collapseChangedListener != null) {
      collapseChangedListener.onCollapseChanged(collapsedProgress);
    }
  }

  public interface CollapseChangedListener {

    void onCollapseChanged(float collapsedProgress);
  }
}
