/**
 * 
 */
package server;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * @author Lata
 *
 */
public class IRequest extends StringRequest{

	public IRequest(String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(url, listener, errorListener);
		// TODO Auto-generated constructor stub
	}

	/*public ZoomRequest(int method, String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		// TODO Auto-generated constructor stub
	}*/
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		// TODO Auto-generated method stub
		return super.getParams();
	}
	@Override
	protected VolleyError parseNetworkError(VolleyError volleyError) {
		// TODO Auto-generated method stub
		return super.parseNetworkError(volleyError);
	}
	
	

}
