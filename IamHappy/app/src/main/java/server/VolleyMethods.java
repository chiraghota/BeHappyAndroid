package server;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import iamhappy.in.iamhappy.R;
import listener.OnErrorMessageListener;

public class VolleyMethods {
	
	private static final String FTAG = "VolleyMetods";
	public Activity mContext;
	private OnErrorMessageListener onErrorMessageListener;

	public VolleyMethods(Activity Context) {
		mContext = Context;
	}

	public Response.ErrorListener createErrorListener(final int requestCode,final String requestName, final String requestPath, final String requestParams) {

		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

				try {
					if (error instanceof NetworkError) {
						handleErrorResponse(requestCode, new String(error.networkResponse.data));
						
					} else if (error instanceof ServerError) {
						handleErrorResponse(requestCode, new String(error.networkResponse.data));
					} else if (error instanceof AuthFailureError) {
						handleErrorResponse(requestCode, new String(error.networkResponse.data));
						
					} else if (error instanceof ParseError) {
						handleErrorResponse(requestCode, new String(error.networkResponse.data));
					} else if (error instanceof NoConnectionError) {
						noConnectionError(requestCode);
					} else if (error instanceof TimeoutError) {
						timeoutError(requestCode);
					}
				} catch (Exception e) {
					if (error instanceof NoConnectionError) {
						noConnectionError(requestCode);
					}
				}
			}
		};
	}

	protected void handleErrorResponse(final int requestCode, String msg) {
		if(getOnErrorMessageListener() != null){
			getOnErrorMessageListener().onError(requestCode, msg);
		}
	}
	private void timeoutError(final int requestCode) {
		try{
			handleErrorResponse(requestCode, mContext.getResources().getString(R.string.msg_timeout_error));
		}catch(Exception e){
		}
	}

	private void noConnectionError(final int requestCode) {
		try{
			handleErrorResponse(requestCode, mContext.getResources().getString(R.string.msg_network_not_available));
		}catch(Exception e){
		}

	}

	public OnErrorMessageListener getOnErrorMessageListener() {
		return onErrorMessageListener;
	}

	public void setOnErrorMessageListener(OnErrorMessageListener onErrorMessageListener) {
		this.onErrorMessageListener = onErrorMessageListener;
	}
}
