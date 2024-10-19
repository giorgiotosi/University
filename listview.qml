import QtQuick 2.15
import QtQuick.Controls 2.15
import QtQuick.Shapes 1.15
import QtQuick3D
import QtQuick3D.Helpers

ApplicationWindow {
    visible: true
    width: 400
    height: 600

    ListView {
        width: parent.width
        height: parent.height
        model: listaModel

        delegate: Item {
            id: delegate
            width: parent.width
            height: 80
            Rectangle {
                id: productRectangle
                width: parent.width
                height: parent.height
                color: "lightgray"
                border.color: "black"

                ShaderEffect {
                    id: shadowEffect
                    anchors.fill: parent
                    property real shadowX: 20
                    property real shadowY: 20
                    property real shadowOpacity: 0.3

                    fragmentShader: "
                        varying vec2 qt_TexCoord0;
                        uniform sampler2D source;
                        uniform float shadowX;
                        uniform float shadowY;
                        uniform float shadowOpacity;

                        void main() {
                            // Colore dell'ombra
                            vec4 shadowColor = vec4(0.0, 0.0, 0.0, shadowOpacity);
                            // Calcola l'ombreggiatura
                            vec2 shadowCoord = qt_TexCoord0 + vec2(shadowX, shadowY);
                            gl_FragColor = shadowColor * smoothstep(0.0, 1.0, 1.0 - length(shadowCoord - qt_TexCoord0));
                        }
                    "
                }

                Column {
                    anchors.fill: parent
                    anchors.margins: 10

                    Text {
                        text: model.nome + " " + model.prezzo // Mostra il nome e prezzo del prodotto
                        font.pixelSize: 20
                    }
                }



                //ToolTip
                ToolTip {
                    id: productToolTip
                    text: model.descrizione // Questo deve rimandare al giusto riferimento
                    background: Rectangle {
                        color: "white" // Colore di sfondo
                        radius: 5 // Raggio degli angoli
                    }
                }

                //MouseArea per il passaggio del mouse
                MouseArea {
                    anchors.fill: parent
                    hoverEnabled: true

                    onEntered: {
                        shadowEffect.visible = true
                        //discountButton.background.color = "#A5D6A7"
                        productRectangle.color = "lightblue";
                        productToolTip.visible = true; // Mostra il ToolTip quando il cursore entra
                        productToolTip.x = productRectangle.x + productRectangle.width - productToolTip.width - 10; // Posiziona a destra all'interno
                        productToolTip.y = productRectangle.y + productRectangle.height - productToolTip.height - 10; // Posiziona in basso all'interno

                    }

                    onExited: {
                        shadowEffect.visible = false
                        //discountButton.background.color = "#C8E6C9"
                        productRectangle.color = "lightgray";
                        productToolTip.visible = false; // Nasconde il ToolTip quando il cursore esce
                    }
                }
            }

            Button {
                id: addButton
                text: "+"
                font.bold: true // Testo in grassetto
                font.pixelSize: 18 // Aumenta la dimensione del carattere
                anchors.top: delegate.bottom
                anchors.horizontalCenter: delegate.horizontalCenter
                anchors.margins: 10 // Margini dal bordo
                background: Rectangle {
                    color: "#B0B0B0" // Colore grigio sporco
                    radius: 5 // Raggio degli angoli
                    border.color: "black" // Colore del bordo
                    border.width: 1 // Spessore del bordo
                }
                MouseArea {
                    anchors.fill: parent
                    onClicked: {
                        // Apri la finestra di dialogo per l'inserimento dei dati
                        addProductDialog.open();
                    }
                }
            }

            Dialog {
                id: addProductDialog
                title: "Aggiungi Prodotto"
                visible: false
                width: 300
                height: 300
                x: parent.width * 0.55
                y: parent.height * 0.45

                Column {
                    anchors.fill: parent
                    spacing: 10
                    padding: 10

                    // Campo per il nome
                    Column {
                        width: parent.width
                        spacing: 5

                        // Testo "Nome" sopra il campo di input
                        Text {
                            id: nomeText
                            text: "Nome"
                        }

                        // TextInput con placeholder
                        Item {
                            width: parent.width
                            height: 20

                            TextInput {
                                id: nomeInput
                                anchors.fill: parent



                                // Label come Placeholder visibile solo quando l'input è vuoto
                                Label {
                                    id: placeholderLabelNome
                                    text: "Inserisci il nome"
                                    anchors.verticalCenter: parent.verticalCenter
                                    anchors.left: parent.left
                                    color: "gray"
                                    visible: nomeInput.text === "" // Nascondi quando c'è testo
                                    //z: 1 // Porta il placeholder sopra il TextInput
                                }
                            }
                        }
                    }

                    // Campo per il prezzo
                    Column {
                        width: parent.width
                        spacing: 5

                        Text {
                            text: "Prezzo"
                        }

                        Item {
                            width: parent.width
                            height: 20

                            TextInput {
                                id: prezzoInput
                                anchors.fill: parent



                                Label {
                                    id: placeholderLabelPrezzo
                                    text: "Inserisci il prezzo"
                                    anchors.verticalCenter: parent.verticalCenter
                                    anchors.left: parent.left
                                    color: "gray"
                                    visible: prezzoInput.text === "" // Nascondi quando c'è testo
                                    //z: 1
                                }
                            }
                        }
                    }

                    // Campo per la descrizione
                    Column {
                        width: parent.width
                        spacing: 5

                        Text {
                            text: "Descrizione"
                        }

                        Item {
                            width: parent.width
                            height: 20

                            TextInput {
                                id: descrizioneInput
                                anchors.fill: parent



                                Label {
                                    id: placeholderLabelDescrizione
                                    text: "Inserisci la descrizione"
                                    anchors.verticalCenter: parent.verticalCenter
                                    anchors.left: parent.left
                                    color: "gray"
                                    visible: descrizioneInput.text === "" // Nascondi quando c'è testo
                                    //z: 1
                                }

                            }
                        }
                    }

                    // Bottone OK per confermare e chiudere
                    Button {
                        visible: true
                        text: "OK"
                        anchors.horizontalCenter: parent.horizontalCenter
                        onClicked: {
                            listaModel.addProduct(nomeInput.text, prezzoInput.text, descrizioneInput.text);
                            addProductDialog.close(); // Chiude il dialogo
                        }
                    }
                }
            }







            Button {
                id: discountButton
                text: "Apply 10% off"
                anchors.right: productRectangle.right
                anchors.top: productRectangle.top
                anchors.margins: 10 // Margini dal bordo
                background: Rectangle {
                    color: "#C8E6C9" //verde pastello
                    radius: 5 // Raggio degli angoli
                    border.color: "#4CAF50" // Colore del bordo
                    border.width: 1 // Spessore del bordo

                }
                MouseArea {
                    anchors.fill: parent
                    hoverEnabled: true
                    onEntered: {
                        //discountButton.background.color = "#DCE775" //verde lime chiaro
                    }
                    onExited: {
                        discountButton.background.color = "#C8E6C9"
                    }
                    onClicked: {
                        listaModel.applyDiscount(model.index);
                        discountButton.background.color = "#A5D6A7" //verde menta
                    }
                }

            }
        }
    }
}

