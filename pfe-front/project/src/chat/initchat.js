import "@n8n/chat/style.css";

let chatInitialized = false;

export function initChat() {
  if (chatInitialized) return; // évite double chargement

  import("@n8n/chat")
    .then(({ createChat }) => {
      createChat({
        webhookUrl:
          "https://douae8.app.n8n.cloud/webhook/441e733c-530d-4e57-8bcc-c91a8ea9c3bf/chat",
        theme: {
          color: "#04122c",
          title: "Inventory Chatbot",
          position: "bottom-right",
        },
      });

      chatInitialized = true;
    })
    .catch((err) => {
      console.error("Chat init failed:", err);
    });
}