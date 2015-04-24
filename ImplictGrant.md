An Authorisation Grant is the credetial representation the Resource Owner's authorisation of the Client's access to its protected resources

# Introduction #

Implicit Grant is a simplified Authorisation code workflow. Here the client is issued the Access tokens, just after the Resources owner's authentication. The access token is thus returned, when the user-agent is redirected to the Client


# Details #

> This implies that

> No Authorisation Code is required by the Client to obtain Access tokens. They are issued directly on redirection, immediately after resource owner authentication.
> Authorisation server does not Authenticate the Client
> Access tokens issued are now visible to the user-agent. This can pose some security loops

Because of the above three restrictions, this type authorisation grant workflow is optimised for Clients that are implemented in the Browser
s