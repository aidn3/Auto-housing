package hypixel.aidn5.housing.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class InternetHandler {

	public void getString(String url, EventListener callback) {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				start(url, callback);

			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
	}

	private void start(String URL_, EventListener eventListener) {
		try {
			URL url = new URL(URL_);
			StringBuilder response = new StringBuilder("");

			URLConnection urlConnection = url.openConnection();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			String inputLine;
			while ((inputLine = bufferedReader.readLine()) != null)
				response.append(inputLine);

			bufferedReader.close();
			if (eventListener != null) eventListener.onFinished(response.toString());
		} catch (Exception e) {
			if (eventListener != null) eventListener.onError(e);
		}
	}

	public interface EventListener {
		public void onFinished(String data);

		public void onError(Exception e);
	}

}
