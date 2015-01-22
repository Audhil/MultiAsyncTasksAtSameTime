package com.wordpress.smdaudhilbe;

import com.wordpress.smdaudhilbe.multiasynctaskatsametimedemo.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	private ProgressBar pBar1, pBar2, pBar3, pBar4;
	private asynCTasks aTask1, aTask2, aTask3, aTask4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
	}

	private void initViews() {
		pBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		pBar2 = (ProgressBar) findViewById(R.id.progressBar2);
		pBar3 = (ProgressBar) findViewById(R.id.progressBar3);
		pBar4 = (ProgressBar) findViewById(R.id.progressBar4);
	}

	@Override
	protected void onResume() {
		super.onResume();

		aTask1 = new asynCTasks();
		aTask1.execute(pBar1);

		aTask2 = new asynCTasks();
		aTask2.execute(pBar2);

		// parallel execution
		aTask3 = new asynCTasks();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			aTask3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, pBar3);
		else
			aTask3.execute(pBar3);

		aTask4 = new asynCTasks();
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			aTask4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, pBar4);
		else
			aTask4.execute(pBar4);
	}

	// asynTasks
	class asynCTasks extends AsyncTask<ProgressBar, Integer, Void> {

		private ProgressBar pBarIs;

		@Override
		protected Void doInBackground(ProgressBar... params) {
			this.pBarIs = params[0];
			for (int inteGerIs = 0; inteGerIs < 100; inteGerIs++) {
				publishProgress(inteGerIs);
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);

			pBarIs.setProgress(values[0]);
		}
	}
}