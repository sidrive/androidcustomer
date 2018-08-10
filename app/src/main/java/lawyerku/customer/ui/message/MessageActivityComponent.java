package lawyerku.customer.ui.message;

import dagger.Subcomponent;
import lawyerku.customer.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                MessageActivityModule.class
        }
)
public interface MessageActivityComponent {
    MessageActivity inject(MessageActivity activity);
}
