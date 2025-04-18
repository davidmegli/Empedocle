package it.unifi.ing.stlab.empedocle.actions.health.measurementSession.consent;

import it.unifi.ing.stlab.empedocle.actions.util.DateUtils;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDao;
import it.unifi.ing.stlab.woodelements.model.WoodElement;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

@Named("woodelement-consent")
@RequestScoped
@WebServlet(urlPatterns = "/woodelement-consent")
public class WoodElementConsentEnricher extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private WoodElementDao woodElementDao;

	public WoodElementConsentEnricher() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		WoodElement woodElement = woodElementDao.findById( new Long(request.getParameter("pid")));
		
		PdfReader reader = new PdfReader(
				"http://" + request.getServerName() + ":" + request.getServerPort() 
				+ "/empedocle-content/common/consenso_trattam_dati_non_degenti_M903D02_C.pdf");
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "inline; filename=" + "consenso_trattamento_dati_non_degenti.pdf");
		OutputStream output = response.getOutputStream();
		
		try {
			PdfStamper stamper = new PdfStamper(reader, output);

			@SuppressWarnings("unchecked")
			HashMap<String, String> info = reader.getInfo();
			info.put(
					"Title",
					"Trattamento dei dati personali - Modulo di consenso e ulteriori determinazioni non degenti");
			stamper.setMoreInfo(info);
			
			PdfContentByte over = stamper.getOverContent(1);
			over.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA,
			          BaseFont.WINANSI, BaseFont.NOT_EMBEDDED), 10);
			
			over.beginText();
			
			// print name and surname
			over.setTextMatrix(120, 665);
			over.showText(woodElement.getName() + " " + woodElement.getSurname());
			
			// print birth place
			over.setTextMatrix(120, 648);
			over.showText(woodElement.getBirthPlace());
			
			// print birth date
			over.setTextMatrix(440, 648);
			over.showText( DateUtils.getString(woodElement.getBirthDate(), "dd/MM/yyyy"));
			
			over.endText();
			
			stamper.close();

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
