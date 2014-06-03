package bfstatsrcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class View extends ViewPart {

	public static final String ID = "BFStatsRCP.view";

	/**
	 * The text control that's displaying the player stats content.
	 */
	private Text messageText;
	
	public void createPartControl(Composite parent) {
		
		String str = "http://api.bf4stats.com/api/playerInfo?plat=ps4&name=FlyNavyHelos&output=json";
		OutputStream ostream; 
		
		try {
			ostream = this.getOutputStream(str);
			this.writeJsonStream(ostream); 
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Composite top = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		top.setLayout(layout);
		// top banner
		Composite banner = new Composite(top, SWT.NONE);
		banner.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false));
		layout = new GridLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 10;
		layout.numColumns = 2;
		banner.setLayout(layout);
		
		// setup bold font
		Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);    
		
		Label l = new Label(banner, SWT.WRAP);
		l.setText("General Stats");
		l.setFont(boldFont);
		l = new Label(banner, SWT.WRAP);
		l.setText("This is a message about the cool Eclipse RCP!");
		
		l = new Label(banner, SWT.WRAP);
		//l.setText("From:");
		l.setFont(boldFont);
    
		final Link link = new Link(banner, SWT.NONE);
		//link.setText("<a>nicole@mail.org</a>");
		link.addSelectionListener(new SelectionAdapter() {    
			public void widgetSelected(SelectionEvent e) {
				MessageDialog.openInformation(getSite().getShell(), "Not Implemented", "Imagine the address book or a new message being created now.");
			}    
		});
    
		l = new Label(banner, SWT.WRAP);
		//l.setText("Date:");
		l.setFont(boldFont);
		l = new Label(banner, SWT.WRAP);
		l.setText("General Stats");
		// message contents
		messageText = new Text(top, SWT.MULTI | SWT.WRAP);
		messageText.setText("BF4 Payer stats go here...");
		messageText.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	public void setFocus() {
		messageText.setFocus();
	}
	
	/**
	 * Returns an OutputStream from a URL.
	 * @param strURL
	 * @return
	 * @throws IOException
	 */
    public OutputStream getOutputStream(String strURL) throws IOException {
    	
    	URL url = null;
		
    	try {
			url = new URL(strURL);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
    	HttpURLConnection con = (HttpURLConnection) url.openConnection();
    	con.setDoOutput(true);
    	con.setDoInput(true);
    	con.setRequestProperty("Content-Type", "application/json");
    	con.setRequestProperty("Accept", "application/json");
    	con.setRequestMethod("POST");
    	
    	OutputStream os = con.getOutputStream();
		return os;
    }
	
    /**
     * Creates a JSON factory object from an outputs stream an builds POJOs as required.
     * @param out
     * @throws IOException
     */
    public void writeJsonStream(OutputStream out) throws IOException {
    	   JsonFactory f = new JsonFactory();
    	   JsonGenerator g = f.createJsonGenerator(out);

    }

}
