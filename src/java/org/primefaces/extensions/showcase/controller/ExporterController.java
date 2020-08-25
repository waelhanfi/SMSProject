package org.primefaces.extensions.showcase.controller;

import java.io.Serializable;
import java.lang.Boolean;
import java.math.BigDecimal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;


/**
 * ExporterController
 *
 * @author  Sudheer Jonna / last modified by $Author$
 * @version $Revision$
 * @since   1.0
 */
@ManagedBean
@ApplicationScoped
public class ExporterController implements Serializable {

	private static final long serialVersionUID = 20120316L;

	private Boolean customExporter;


	public ExporterController() {
             customExporter=false;
	}

    public Boolean getCustomExporter() {
        return customExporter;
    }

    public void setCustomExporter(Boolean customExporter) {
        this.customExporter = customExporter;
    }
}