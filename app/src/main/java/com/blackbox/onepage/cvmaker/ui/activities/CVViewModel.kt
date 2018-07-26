package com.blackbox.onepage.cvmaker.ui.activities

import android.arch.lifecycle.ViewModel
import com.blackbox.onepage.cvmaker.data.repositories.cv.CVRepository
import javax.inject.Inject

class CVViewModel @Inject constructor(private var cvRepository: CVRepository) : ViewModel() {


}