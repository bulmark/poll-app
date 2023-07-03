export const defineCancelApiObject = (apiObjet) => {
  const cancelApiObject = {};

  for (let apiPropertyName of Object.getOwnPropertyNames(apiObjet)) {
    const cancelControllerObject = {
      controller: undefined,
    };

    cancelApiObject[apiPropertyName] = {
      handleRequestCancellation: () => {
        if (cancelControllerObject.controller) {
          cancelApiObject.abort();
        }

        cancelControllerObject.controller = new AbortController();
        return cancelControllerObject.controller;
      },
    };
  }

  return cancelApiObject;
};
