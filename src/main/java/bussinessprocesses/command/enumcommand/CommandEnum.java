package bussinessprocesses.command.enumcommand;

import bussinessprocesses.command.ActionCommand;
import bussinessprocesses.command.mainpages.*;

/**
 * Created by jacksparrow on 01.10.17.
 */
public enum CommandEnum {

    INDEX {
        {
            this.command = new IndexPageCommand();
        }
    },
    LOGIN {
        {
            this.command = new LoginPageCommand();
        }
    },
    MAIN {
        {
            this.command = new MainPageCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    CABINET {
        {
            this.command = new CabinetPageCommand();
        }
    },
    REGISTRATION {
        {
            this.command = new RegistrationPageCommand();
        }
    },
    HEADER {
        {
            this.command = new HeaderCommand();
        }
    },
    FOOTER {
        {
            this.command = new FooterCommand();
        }
    },
    GOODS {
        {
            this.command = new GoodsPageCommand();
        }
    },
    ADMIN {
        {
            this.command = new AdminPageCommand();
        }
    },
    ADDNEWS {
        {
            this.command = new AddNewsPageCommand();
        }
    },
    NEWSDETAILS {
        {
            this.command = new NewsDetailsPageCommand();
        }
    },
    MAIL {
        {
            this.command = new MailPageCommand();
        }
    },
    CART {
        {
            this.command = new CartPageCommand();
        }
    },
    CALLBACK {
        {
            this.command = new CallbackPageCommand();
        }
    },
    EDITPROFILE {
        {
            this.command = new EditProfilePageCommand();
        }
    },
    FORGOTPASSWORD {
        {
            this.command = new ForgotPasswordPageCommand();
        }
    };

    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
