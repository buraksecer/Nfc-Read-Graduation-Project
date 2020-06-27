package com.burak.nfcokutogren;

import java.util.Locale;

import com.burak.nfcokutogren.R.drawable;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Egitim extends ActionBarActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_egitim);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	

	

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_egitim,
					container, false);
			TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			ImageButton img=(ImageButton) rootView.findViewById(R.id.imageButton1);
			ImageView circle1=(ImageView) rootView.findViewById(R.id.circle1);
			ImageView circle2=(ImageView) rootView.findViewById(R.id.circle2);
			ImageView circle3=(ImageView) rootView.findViewById(R.id.circle3);
			ImageView howto=(ImageView) rootView.findViewById(R.id.imageView1);
			ImageView howtoread=(ImageView) rootView.findViewById(R.id.imageView2);
			ImageView tag=(ImageView) rootView.findViewById(R.id.imageView3);
			final AnimationDrawable animationDrawable = (AnimationDrawable) howto.getDrawable();
			

			//Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER))
			if(getArguments().getInt(
					ARG_SECTION_NUMBER)==1){
				circle2.setBackgroundResource(drawable.circlefull);
				textView.setTypeface(Typeface.SERIF);
				textView.setText("ADIM 1:Telefonunuzun Nfc Özelliğini Açınız.");
				img.setEnabled(false);
				img.setVisibility(-1);
			    howto.setVisibility(-1);
			    tag.setVisibility(-1);
				
				
			}
			else if(getArguments().getInt(
					ARG_SECTION_NUMBER)==2){
				circle1.setBackgroundResource(drawable.circlefull);
				textView.setTypeface(Typeface.SERIF);
				textView.setText("ADIM 2:Uygulamaya Giriş Yaptıktan Sonra NFC Etiketine Yaklaştırınız.");
				img.setVisibility(-1);
				howto.setVisibility(-1);
				
				howtoread.setVisibility(-1);
			}
			else{
				circle3.setBackgroundResource(drawable.circlefull);
				textView.setTypeface(Typeface.SERIF);
				textView.setText("ADIM 3:Okuma Tamamlandıktan Sonra Bilgileri Kayıt Edebilirsiniz.");
				howtoread.setVisibility(-1);
				 tag.setVisibility(-1);
				animationDrawable.start();
				img.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Intent go=new Intent(getActivity(), NfcOkut.class);
						startActivity(go);
						
					}
				});
			
			}
			
		return rootView;
		}
	}

}
