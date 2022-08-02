# devday-demo-flow

## Notes on theme folder issues

This branch serves as a minimal example illustrating hot reload issues when using an externalized theme folder instead of @CssImport.

* separate maven project containing the style (themefolder project)
* added dependency to this project
* IDE: Eclipse
* both projects imported and open in the workspace
* run configuration **with enabled workspace resolution** for `mvn jetty:run`

### Expected behavior

* I can start jetty using the run configuration (with enabled workspace resulution)
* The applications starts and works
* I can make changes in the styles.css of the themefolder project; these changes are seen in the running application

### Actual behavior

* The application starts but throws ExecutionFailedException (Theme annotation found but theme directory not found)

### Further notes

If the theme folder project was installed (mvn install) and is closed or if the workspace resolution is disabled, the application starts.
In this case the theme folder is copied to `target/frontend/themes` and consequently is available.

This is a workaround but not a very nice one for "fast" development.
* Styling changes must be made to the copied css files and then transferred back to the files under version control.
* In our case the theme folder project is a framework project. It contains Vaadin-related code used by several applications as well as common styles. 

Using @CssImport and workspace resolution, it was possible to change framework and styling simultaneously and the jetty reload allowed us to see the changes in the running application.
After a refactoring to the theme folder import, this is not possible anymore because the styles from the separate project are not picked up.

## Vaadin 14+ demo application (DevDays May / 2019)

This is demo application is collection of tips-and-tricks and workarounds.
The source of ideas are questions in Vaadin's support channels (Forum, StackOverflow, 
GitHub issue tracker, commercial support, ...)

This application is not architectural demo about the best progrmming practices.

## Setup

Clone project, import it to your IDE and run it with

## Start server

mvn jetty:run

Production mode

mvn jetty:run -Pproduction

## Open project in Browser

http://localhost:8080/myapp

## List of demos

### Login

- Store intended route to Session and use later to route to right view after login

### InitListener

- Use global before enter event for login purposes
- Setup custom error handler
- Give notification if app is open on another tab

### MainLayout

- Configure context root to be "myapp"
- AppLayout with key shortcuts
- Get browser window width
- Find host address
- PageConfigurator example
- Detect Browser window closing by listening "beforeUnload" with JavaScript and @ClientCallable callback
- Setup CustomizedSystemMessages

### AbsoluteLayoutView

- Simplified absolute layout example

### AccordionView

- The complete demo 

### DialogView

- Simple dialog example
- Form and binding with Bean
- DatePicker with weekend and holiday styles

### FormLayoutView

- Form and binding with Bean 
- Validators
- Access main layout menu
- BeforeLeaveObserver

### GridView

- Custom select column for single select
- Grid context menu
- ContextMenu to jump to years
- ContextMenu to filter by month
- Dynamic cell styling with TemplateRenderer
- Cell styling with setClassNameGenerator
- ComponentRenderer example
- Popup to hide columns
- CheckBox styling in Popup
- Injecting custom property to text field to programmatically change color
- Setup client side event listener with @ClientCallable callback
- Using single RadioButton element from the RadioButtonGroup

### UploadView

- Drag and drop upload example

### SplitLayoutView

- Workaround with Chart resizing, forced re-flow by JavaScript call

### VaadinBoardView

- Chart re-flow workaround by div wrapping
- Charts customization and styling to fit dark theme

### ChartUtil

- Chart drill-down
- Chart plotlines
- Chart tooltip formatter with conditional JavaScript logic 
- Chart zooming / panning

### ThemeVariantsView

- Some theme variant examples

### GridProView

- Workaround issue clicking save button when editor is open

### TreeGridView

- Selected parents subtree highlighting with class name generator
