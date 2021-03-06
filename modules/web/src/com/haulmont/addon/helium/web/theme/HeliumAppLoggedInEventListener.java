/*
 * Copyright (c) 2008-2020 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haulmont.addon.helium.web.theme;

import com.haulmont.cuba.web.AppUI;
import com.haulmont.cuba.web.security.events.AppLoggedInEvent;
import com.vaadin.server.Page;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Objects;

@Component(HeliumAppLoggedInEventListener.NAME)
public class HeliumAppLoggedInEventListener implements ApplicationListener<AppLoggedInEvent> {

    public static final String NAME = "helium_HeliumAppLoggedInEventListener";

    @Inject
    private HeliumThemeVariantsManager variantsManager;

    @Override
    public void onApplicationEvent(AppLoggedInEvent event) {
        boolean authenticated = AppUI.getCurrent().hasAuthenticatedSession();
        if (authenticated) {
            String modeCookie = variantsManager.getUserAppThemeMode();
            String mode = variantsManager.loadUserAppThemeModeSetting();

            String sizeCookie = variantsManager.getUserAppThemeSize();
            String size = variantsManager.loadUserAppThemeSizeSetting();

            if (!Objects.equals(modeCookie, mode)
                    || !Objects.equals(sizeCookie, size)) {
                // if either modes or sizes are not equal, user settings take precedence
                variantsManager.setUserAppThemeMode(mode);
                variantsManager.setUserAppThemeSize(size);

                Page.getCurrent().reload();
            }
        }
    }
}
