### Analysis results

Spanning 58 pages, the Analysis.pdf document (within the 'Docs' folder) contains detailed descriptions of the classes responsible for user management, library management, Last.fm integration and Administrator features.
Their functionality and behaviour are documented, and UML diagrams are used with an appropriate level of abstraction to paint a clear and concise picture of the system.

#### Design smells:
Structures and patterns in the code that, while not incorrect, are indicative of violating fundamental design principles, are identified. These can hinder development by leading to recurring problems down the line and should be avoided. Many automated tools exist to identify these. We will use Sonarqube for serving this purpose in our project.

Sonarqube identifies code smells in a given repository. Code smells are closely related to design smells, but are more specific in nature.

#### Code metrics:
Code metrics are objective, repeatable, empirical measure of a project’s properties. We use third party tools to get some metrics for our project in its initial state. 

#### Refactoring:

We then proceed to to fix the identified design smells by refactoring the code to improve its design. However, we do not want to throw away the previous code or fundamentally change its functionality.

We then remeasure the metrics previously measured after refactoring, check whether they have improved or worsened, and whether the trend is indeed consistent across all metrics. We attempt to analyze what contributed to this.
