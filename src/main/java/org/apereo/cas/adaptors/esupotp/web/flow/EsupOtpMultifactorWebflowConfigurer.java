package org.apereo.cas.adaptors.esupotp.web.flow;

import org.apereo.cas.web.flow.AbstractCasWebflowConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;

/**
 * This is {@link EsupOtpMultifactorWebflowConfigurer}.
 *
 * @author Alex Bouskine
 * @since 5.0.0
 */
@Component("esupotpMultifactorWebflowConfigurer")
public class EsupOtpMultifactorWebflowConfigurer extends AbstractCasWebflowConfigurer {

    /** Webflow event id. */
    public static final String MFA_ESUPOTP_EVENT_ID = "mfa-esupotp";

    @Autowired
    @Qualifier("esupotpFlowRegistry")
    private FlowDefinitionRegistry esupotpFlowRegistry;

    @Override
    protected void doInitialize() throws Exception {
        registerMultifactorProviderAuthenticationWebflow(getLoginFlow(), MFA_ESUPOTP_EVENT_ID, this.esupotpFlowRegistry);
    }
}
