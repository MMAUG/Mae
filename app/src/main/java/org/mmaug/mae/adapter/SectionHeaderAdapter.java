package org.mmaug.mae.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Arrays;
import java.util.Comparator;
import org.mmaug.mae.Config;
import org.mmaug.mae.utils.FontCache;
import org.mmaug.mae.utils.MMTextUtils;
import org.mmaug.mae.utils.UserPrefUtils;
import org.mmaug.mae.view.AutofitTextView;

/**
 * Created by poepoe on 19/9/15.
 */
public class SectionHeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final int SECTION_TYPE = 0;
  private final Context mContext;
  private boolean mValid = true;
  private int mSectionResourceId;
  private int mTextResourceId;
  private RecyclerView.Adapter mBaseAdapter;
  private SparseArray<Section> mSections = new SparseArray<>();
  private RecyclerView mRecyclerView;

  private Typeface typeface;
  private boolean isUni;
  private MMTextUtils mmTextUtils;

  public SectionHeaderAdapter(Context context, int sectionResourceId, int textResourceId,
      RecyclerView recyclerView, RecyclerView.Adapter baseAdapter) {

    LayoutInflater mLayoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mSectionResourceId = sectionResourceId;
    mTextResourceId = textResourceId;
    mBaseAdapter = baseAdapter;
    mContext = context;
    mRecyclerView = recyclerView;

    mBaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
      @Override public void onChanged() {
        mValid = mBaseAdapter.getItemCount() > 0;
        notifyDataSetChanged();
      }

      @Override public void onItemRangeChanged(int positionStart, int itemCount) {
        mValid = mBaseAdapter.getItemCount() > 0;
        notifyItemRangeChanged(positionStart, itemCount);
      }

      @Override public void onItemRangeInserted(int positionStart, int itemCount) {
        mValid = mBaseAdapter.getItemCount() > 0;
        notifyItemRangeInserted(positionStart, itemCount);
      }

      @Override public void onItemRangeRemoved(int positionStart, int itemCount) {
        mValid = mBaseAdapter.getItemCount() > 0;
        notifyItemRangeRemoved(positionStart, itemCount);
      }
    });

    final GridLayoutManager layoutManager = (GridLayoutManager) (mRecyclerView.getLayoutManager());
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override public int getSpanSize(int position) {
        return (isSectionHeaderPosition(position)) ? layoutManager.getSpanCount() : 1;
      }
    });
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int typeView) {
    if (typeView == SECTION_TYPE) {
      //get typeface
      typeface = FontCache.getTypefaceLight(mContext);
      //check user has choose unicode or not
      isUni = UserPrefUtils.getInstance(mContext).getTextPref().equals(Config.UNICODE);
      mmTextUtils = MMTextUtils.getInstance(mContext);

      final View view = LayoutInflater.from(mContext).inflate(mSectionResourceId, parent, false);
      return new SectionViewHolder(view, mTextResourceId);
    } else {
      return mBaseAdapter.onCreateViewHolder(parent, typeView - 1);
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder sectionViewHolder, int position) {
    if (isSectionHeaderPosition(position)) {
      ((SectionViewHolder) sectionViewHolder).title.setText(mSections.get(position).title);
      if (isUni) {
        ((SectionViewHolder) sectionViewHolder).title.setTypeface(typeface);
      } else {
        mmTextUtils.prepareSingleView(((SectionViewHolder) sectionViewHolder).title);
      }
    } else {
      mBaseAdapter.onBindViewHolder(sectionViewHolder, sectionedPositionToPosition(position));
    }
  }

  @Override public int getItemViewType(int position) {
    return isSectionHeaderPosition(position) ? SECTION_TYPE
        : mBaseAdapter.getItemViewType(sectionedPositionToPosition(position)) + 1;
  }

  public void setSections(Section[] sections) {
    mSections.clear();

    Arrays.sort(sections, new Comparator<Section>() {
      @Override public int compare(Section o, Section o1) {
        return (o.firstPosition == o1.firstPosition) ? 0
            : ((o.firstPosition < o1.firstPosition) ? -1 : 1);
      }
    });

    int offset = 0; // offset positions for the headers we're adding
    for (Section section : sections) {
      section.sectionedPosition = section.firstPosition + offset;
      mSections.append(section.sectionedPosition, section);
      ++offset;
    }

    notifyDataSetChanged();
  }

  public int positionToSectionedPosition(int position) {
    int offset = 0;
    for (int i = 0; i < mSections.size(); i++) {
      if (mSections.valueAt(i).firstPosition > position) {
        break;
      }
      ++offset;
    }
    return position + offset;
  }

  public int sectionedPositionToPosition(int sectionedPosition) {
    if (isSectionHeaderPosition(sectionedPosition)) {
      return RecyclerView.NO_POSITION;
    }

    int offset = 0;
    for (int i = 0; i < mSections.size(); i++) {
      if (mSections.valueAt(i).sectionedPosition > sectionedPosition) {
        break;
      }
      --offset;
    }
    return sectionedPosition + offset;
  }

  public boolean isSectionHeaderPosition(int position) {
    return mSections.get(position) != null;
  }

  @Override public long getItemId(int position) {
    return isSectionHeaderPosition(position) ? Integer.MAX_VALUE - mSections.indexOfKey(position)
        : mBaseAdapter.getItemId(sectionedPositionToPosition(position));
  }

  @Override public int getItemCount() {
    return (mValid ? mBaseAdapter.getItemCount() + mSections.size() : 0);
  }

  public static class SectionViewHolder extends RecyclerView.ViewHolder {

    public AutofitTextView title;

    public SectionViewHolder(View view, int mTextResourceid) {
      super(view);
      title = (AutofitTextView) view.findViewById(mTextResourceid);
      title.setSizeToFit(true);
    }
  }

  public static class Section {
    int firstPosition;
    int sectionedPosition;
    String title;

    public Section(int firstPosition, String title) {
      this.firstPosition = firstPosition;
      this.title = title;
    }

    public String getTitle() {
      return title;
    }
  }
}
