const authConfig = {
    authority: 'https://security-bzvymn.zitadel.cloud', //Replace with your issuer URL
    client_id: '270265983627815536@angmetal-front', //Replace with your client id
    redirect_uri: 'http://localhost:3000/callback',
    response_type: 'code',
    scope: 'openid profile email',
    post_logout_redirect_uri: 'http://localhost:3000/',
    userinfo_endpoint: 'https://security-bzvymn.zitadel.cloud/oidc/v1/userinfo', //Replace with your user-info endpoint
    response_mode: 'query',
    code_challenge_method: 'S256',
  };

 export default authConfig;
