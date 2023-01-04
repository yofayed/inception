/*
 * Licensed to the Technische Universität Darmstadt under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The Technische Universität Darmstadt 
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.
 *  
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.tudarmstadt.ukp.clarin.webanno.ui.project.users;

import static de.tudarmstadt.ukp.clarin.webanno.model.PermissionLevel.ANNOTATOR;
import static de.tudarmstadt.ukp.clarin.webanno.model.PermissionLevel.CURATOR;
import static de.tudarmstadt.ukp.clarin.webanno.model.PermissionLevel.MANAGER;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.AbstractChoice.LabelPosition;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;

import de.tudarmstadt.ukp.clarin.webanno.api.ProjectService;
import de.tudarmstadt.ukp.clarin.webanno.model.PermissionLevel;
import de.tudarmstadt.ukp.clarin.webanno.model.Project;
import de.tudarmstadt.ukp.clarin.webanno.model.ProjectUserPermissions;
import de.tudarmstadt.ukp.clarin.webanno.security.UserDao;
import de.tudarmstadt.ukp.clarin.webanno.support.lambda.LambdaAjaxButton;
import de.tudarmstadt.ukp.clarin.webanno.support.lambda.LambdaAjaxLink;
import de.tudarmstadt.ukp.clarin.webanno.support.lambda.LambdaModelAdapter;

public class UserPermissionsPanel
    extends Panel
{
    private static final long serialVersionUID = -5278078988218713188L;

    private @SpringBean ProjectService projectRepository;
    private @SpringBean UserDao userRepository;

    private IModel<Project> project;
    private IModel<ProjectUserPermissions> user;
    private Form<Void> form;
    private CheckBoxMultipleChoice<PermissionLevel> levels;

    public UserPermissionsPanel(String aId, IModel<Project> aProject,
            IModel<ProjectUserPermissions> aUser)
    {
        super(aId);

        setOutputMarkupId(true);
        setOutputMarkupPlaceholderTag(true);

        project = aProject;
        user = aUser;

        form = new Form<>("form");
        add(form);

        levels = new CheckBoxMultipleChoice<>("permissions");
        levels.setPrefix("<div class=\"checkbox\">");
        levels.setSuffix("</div>");
        levels.setLabelPosition(LabelPosition.WRAP_AFTER);
        // This model adapter handles loading/saving permissions directly to the DB
        levels.setModel(new LambdaModelAdapter<Collection<PermissionLevel>>( //
                () -> {
                    return projectRepository.listRoles(project.getObject(),
                            user.map(ProjectUserPermissions::getUsername).getObject());
                }, //
                (lvls) -> projectRepository.setProjectPermissionLevels(
                        user.map(ProjectUserPermissions::getUsername).getObject(),
                        project.getObject(), lvls)));
        levels.setChoices(asList(MANAGER, CURATOR, ANNOTATOR));
        levels.setChoiceRenderer(new EnumChoiceRenderer<>(levels));
        levels.add(this::ensureManagersNotRemovingThemselves);
        form.add(levels);

        form.add(new Label("username", PropertyModel.of(aUser, "username")));
        form.add(new LambdaAjaxButton<>("save", this::actionSave));
        form.add(new LambdaAjaxLink("cancel", this::actionCancel));
    }

    @Override
    protected void onModelChanged()
    {
        levels.modelChanged();
        form.modelChanged();
        super.onModelChanged();
    }

    @Override
    protected void onConfigure()
    {
        super.onConfigure();

        setVisible(user.getObject() != null);
    }

    private void ensureManagersNotRemovingThemselves(
            IValidatable<Collection<PermissionLevel>> aValidatable)
    {
        if (!userRepository.getCurrentUsername()
                .equals(user.map(ProjectUserPermissions::getUsername).orElse(null).getObject())) {
            return;
        }

        if (!projectRepository.hasRole(user.map(ProjectUserPermissions::getUsername).getObject(),
                project.getObject(), MANAGER)) {
            return;
        }

        if (!aValidatable.getValue().contains(MANAGER)) {
            aValidatable.error(new ValidationError(
                    "Managers cannot remove their own manager role from a project"));
        }
    }

    private void actionSave(AjaxRequestTarget aTarget, Form<Void> aForm)
    {
        // The model adapter already commits the changes for us as part of the normal form
        // processing cycle. So nothing special to do here.

        success("Permissions saved");

        // Reload whole page because master panel also needs to be reloaded.
        aTarget.add(getPage());
    }

    private void actionCancel(AjaxRequestTarget aTarget)
    {
        user.setObject(null);

        // Reload whole page because master panel also needs to be reloaded.
        aTarget.add(getPage());
    }
}
