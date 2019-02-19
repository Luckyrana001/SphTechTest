package com.sphtech.application;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityExpressoItemCountTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureTextChangesWork() throws InterruptedException {
        Thread.sleep(1000);
        if (getRVcount() > 0){
            System.out.print("Total Items"+getRVcount());
        }else{
            System.out.print("No Data Found");
        }
    }


    private int getRVcount(){
        RecyclerView recyclerView = (RecyclerView) mActivityRule.getActivity().findViewById(R.id.md_contentRecyclerView);
        return recyclerView.getAdapter().getItemCount();
    }



}