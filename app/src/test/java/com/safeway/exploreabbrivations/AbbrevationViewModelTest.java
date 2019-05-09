package com.safeway.exploreabbrivations;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;

import com.safeway.exploreabbrivations.viewmodels.AbbrevationViewModel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import static org.junit.Assert.assertEquals;

public class AbbrevationViewModelTest {

    AbbrevationViewModel viewModel = new AbbrevationViewModel();

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void validateEmptyListVisibility(){
        viewModel.emptyListVisibility.postValue(false);
        assertEquals(viewModel.emptyListVisibility.getValue(), false);
    }
}
