export const ucenecSchema = {
  "type": "object",
  "properties": {
    "oseba": {
      "type": "object",
      "properties": {
        "_id": {
          "type": "string"
        },
        "tip": {
          "type": "array",
          "items": [
            {
              "type": "string"
            }
          ]
        },
        "geslo": {
          "type": "string"
        },
        "letnik": {
          "type": "integer"
        },
        "username": {
          "type": "string"
        },
        "ime": {
          "type": "string"
        },
        "priimek": {
          "type": "string"
        }
      },
      "required": [
        "_id",
        "tip",
        "geslo",
        "letnik",
        "username",
        "ime",
        "priimek"
      ]
    },
    "naslov_refs": {
      "type": "array",
      "items": [
        {
          "type": "object",
          "properties": {
            "naslov": {
              "type": "object",
              "properties": {
                "_id": {
                  "type": "string"
                },
                "oseba_id": {
                  "type": "string"
                },
                "drzava": {
                  "type": "string"
                },
                "mesto": {
                  "type": "string"
                },
                "zip": {
                  "type": "string"
                },
                "ulica": {
                  "type": "string"
                },
                "dodatno": {
                  "type": "string"
                }
              },
              "required": [
                "_id",
                "oseba_id",
                "drzava",
                "mesto",
                "zip",
                "ulica",
                "dodatno"
              ]
            }
          },
          "required": [
            "naslov"
          ]
        }
      ]
    },
    "kontakt_refs": {
      "type": "array",
      "items": [
        {
          "type": "object",
          "properties": {
            "kontakt": {
              "type": "object",
              "properties": {
                "_id": {
                  "type": "string"
                },
                "oseba_id": {
                  "type": "array",
                  "items": [
                    {
                      "type": "string"
                    },
                    {
                      "type": "string"
                    },
                    {
                      "type": "string"
                    }
                  ]
                },
                "tip": {
                  "type": "string"
                },
                "data": {
                  "type": "string"
                }
              },
              "required": [
                "_id",
                "oseba_id",
                "tip",
                "data"
              ]
            },
            "sporocilo_prejemnik_refs": {
              "type": "array",
              "items": [
                {
                  "type": "object",
                  "properties": {
                    "sporocilo": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "kontakt_posiljatelj_id": {
                          "type": "string"
                        },
                        "kontakt_prejemnik_id": {
                          "type": "array",
                          "items": [
                            {
                              "type": "string"
                            },
                            {
                              "type": "string"
                            },
                            {
                              "type": "string"
                            },
                            {
                              "type": "string"
                            }
                          ]
                        },
                        "poslano": {
                          "type": "string"
                        },
                        "vsebina": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "kontakt_posiljatelj_id",
                        "kontakt_prejemnik_id",
                        "poslano",
                        "vsebina"
                      ]
                    },
                    "kontakt_refs": {
                      "type": "array",
                      "items": [
                        {
                          "type": "object",
                          "properties": {
                            "kontakt": {
                              "type": "object",
                              "properties": {
                                "_id": {
                                  "type": "string"
                                },
                                "oseba_id": {
                                  "type": "array",
                                  "items": [
                                    {
                                      "type": "string"
                                    },
                                    {
                                      "type": "string"
                                    },
                                    {
                                      "type": "string"
                                    }
                                  ]
                                },
                                "tip": {
                                  "type": "string"
                                },
                                "data": {
                                  "type": "string"
                                }
                              },
                              "required": [
                                "_id",
                                "oseba_id",
                                "tip",
                                "data"
                              ]
                            },
                            "oseba_refs": {
                              "type": "array",
                              "items": [
                                {
                                  "type": "object",
                                  "properties": {
                                    "oseba": {
                                      "type": "object",
                                      "properties": {
                                        "_id": {
                                          "type": "string"
                                        },
                                        "tip": {
                                          "type": "array",
                                          "items": [
                                            {
                                              "type": "string"
                                            }
                                          ]
                                        },
                                        "geslo": {
                                          "type": "string"
                                        },
                                        "letnik": {
                                          "type": "integer"
                                        },
                                        "username": {
                                          "type": "string"
                                        },
                                        "ime": {
                                          "type": "string"
                                        },
                                        "priimek": {
                                          "type": "string"
                                        }
                                      },
                                      "required": [
                                        "_id",
                                        "tip",
                                        "geslo",
                                        "letnik",
                                        "username",
                                        "ime",
                                        "priimek"
                                      ]
                                    }
                                  },
                                  "required": [
                                    "oseba"
                                  ]
                                }
                              ]
                            }
                          },
                          "required": [
                            "kontakt",
                            "oseba_refs"
                          ]
                        }
                      ]
                    }
                  },
                  "required": [
                    "sporocilo",
                    "kontakt_refs"
                  ]
                }
              ]
            },
            "sporocilo_posiljatelj_refs": {
              "type": "array",
              "items": [
                {
                  "type": "object",
                  "properties": {
                    "sporocilo": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "kontakt_posiljatelj_id": {
                          "type": "string"
                        },
                        "kontakt_prejemnik_id": {
                          "type": "array",
                          "items": [
                            {
                              "type": "string"
                            }
                          ]
                        },
                        "poslano": {
                          "type": "string"
                        },
                        "vsebina": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "kontakt_posiljatelj_id",
                        "kontakt_prejemnik_id",
                        "poslano",
                        "vsebina"
                      ]
                    },
                    "kontakt_refs": {
                      "type": "array",
                      "items": [
                        {
                          "type": "object",
                          "properties": {
                            "kontakt": {
                              "type": "object",
                              "properties": {
                                "_id": {
                                  "type": "string"
                                },
                                "oseba_id": {
                                  "type": "array",
                                  "items": [
                                    {
                                      "type": "string"
                                    },
                                    {
                                      "type": "string"
                                    },
                                    {
                                      "type": "string"
                                    }
                                  ]
                                },
                                "tip": {
                                  "type": "string"
                                },
                                "data": {
                                  "type": "string"
                                }
                              },
                              "required": [
                                "_id",
                                "oseba_id",
                                "tip",
                                "data"
                              ]
                            },
                            "oseba_refs": {
                              "type": "array",
                              "items": [
                                {
                                  "type": "object",
                                  "properties": {
                                    "oseba": {
                                      "type": "object",
                                      "properties": {
                                        "_id": {
                                          "type": "string"
                                        },
                                        "tip": {
                                          "type": "array",
                                          "items": [
                                            {
                                              "type": "string"
                                            }
                                          ]
                                        },
                                        "geslo": {
                                          "type": "string"
                                        },
                                        "letnik": {
                                          "type": "integer"
                                        },
                                        "username": {
                                          "type": "string"
                                        },
                                        "ime": {
                                          "type": "string"
                                        },
                                        "priimek": {
                                          "type": "string"
                                        }
                                      },
                                      "required": [
                                        "_id",
                                        "tip",
                                        "geslo",
                                        "letnik",
                                        "username",
                                        "ime",
                                        "priimek"
                                      ]
                                    }
                                  },
                                  "required": [
                                    "oseba"
                                  ]
                                }
                              ]
                            }
                          },
                          "required": [
                            "kontakt",
                            "oseba_refs"
                          ]
                        }
                      ]
                    }
                  },
                  "required": [
                    "sporocilo",
                    "kontakt_refs"
                  ]
                }
              ]
            }
          },
          "required": [
            "kontakt",
            "sporocilo_prejemnik_refs",
            "sporocilo_posiljatelj_refs"
          ]
        }
      ]
    },
    "audit_refs": {
      "type": "array",
      "items": [
        {
          "type": "object",
          "properties": {
            "audit": {
              "type": "object",
              "properties": {
                "_id": {
                  "type": "string"
                },
                "entitete_id": {
                  "type": "array",
                  "items": [
                    {
                      "type": "string"
                    },
                    {
                      "type": "string"
                    },
                    {
                      "type": "string"
                    },
                    {
                      "type": "string"
                    }
                  ]
                },
                "tip": {
                  "type": "string"
                },
                "trajanje": {
                  "type": "string"
                },
                "ustvarjeno": {
                  "type": "string"
                },
                "opis": {
                  "type": "string"
                },
                "entiteta": {
                  "type": "string"
                }
              },
              "required": [
                "_id",
                "entitete_id",
                "tip",
                "trajanje",
                "ustvarjeno",
                "opis",
                "entiteta"
              ]
            }
          },
          "required": [
            "audit"
          ]
        }
      ]
    },
    "test_ucenec_refs": {
      "type": "array",
      "items": [
        {
          "type": "object",
          "properties": {
            "naloga": {
              "type": "object",
              "properties": {
                "_id": {
                  "type": "string"
                },
                "tematika_id": {
                  "type": "string"
                },
                "resitev": {
                  "type": "string"
                },
                "vsebina": {
                  "type": "string"
                }
              },
              "required": [
                "_id",
                "tematika_id",
                "resitev",
                "vsebina"
              ]
            },
            "tematika_refs": {
              "type": "array",
              "items": [
                {
                  "type": "object",
                  "properties": {
                    "tematika": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "zvezek_id": {
                          "type": "string"
                        },
                        "naslov": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "zvezek_id",
                        "naslov"
                      ]
                    }
                  },
                  "required": [
                    "tematika"
                  ]
                }
              ]
            }
          },
          "required": [
            "naloga",
            "tematika_refs"
          ]
        }
      ]
    },
    "ucenje_ucenec_refs": {
      "type": "array",
      "items": [
        {
          "type": "object",
          "properties": {
            "ucenje": {
              "type": "object",
              "properties": {
                "_id": {
                  "type": "string"
                },
                "oseba_ucenec_id": {
                  "type": "string"
                },
                "oseba_ucitelj_id": {
                  "type": "string"
                },
                "ustvarjeno": {
                  "type": "string"
                }
              },
              "required": [
                "_id",
                "oseba_ucenec_id",
                "oseba_ucitelj_id",
                "ustvarjeno"
              ]
            },
            "oseba_refs": {
              "type": "array",
              "items": [
                {
                  "type": "object",
                  "properties": {
                    "oseba": {
                      "type": "object",
                      "properties": {
                        "_id": {
                          "type": "string"
                        },
                        "tip": {
                          "type": "array",
                          "items": [
                            {
                              "type": "string"
                            }
                          ]
                        },
                        "geslo": {
                          "type": "string"
                        },
                        "letnik": {
                          "type": "integer"
                        },
                        "username": {
                          "type": "string"
                        },
                        "ime": {
                          "type": "string"
                        },
                        "priimek": {
                          "type": "string"
                        }
                      },
                      "required": [
                        "_id",
                        "tip",
                        "geslo",
                        "letnik",
                        "username",
                        "ime",
                        "priimek"
                      ]
                    }
                  },
                  "required": [
                    "oseba"
                  ]
                }
              ]
            }
          },
          "required": [
            "ucenje",
            "oseba_refs"
          ]
        }
      ]
    }
  },
  "required": [
    "oseba",
    "naslov_refs",
    "kontakt_refs",
    "audit_refs",
    "test_ucenec_refs",
    "ucenje_ucenec_refs"
  ]
}

export const ucenecOsebaSchema = {
  "type": "object",
  "properties": {
    "oseba": {
      "type": "object",
      "properties": {
        "_id": {
          "type": "string"
        },
        "tip": {
          "type": "array",
          "items": [
            {
              "type": "string"
            }
          ]
        },
        "geslo": {
          "type": "string"
        },
        "letnik": {
          "type": "integer"
        },
        "username": {
          "type": "string"
        },
        "ime": {
          "type": "string"
        },
        "priimek": {
          "type": "string"
        }
      },
      "required": [
        "_id",
        "tip",
        "geslo",
        "letnik",
        "username",
        "ime",
        "priimek"
      ]
    },
    "naslovi": {
      "type": "array",
      "items": [
        {
          "type": "object",
          "properties": {
            "_id": {
              "type": "string"
            },
            "oseba_id": {
              "type": "string"
            },
            "drzava": {
              "type": "string"
            },
            "mesto": {
              "type": "string"
            },
            "zip": {
              "type": "string"
            },
            "ulica": {
              "type": "string"
            },
            "dodatno": {
              "type": "string"
            }
          },
          "required": [
            "_id",
            "oseba_id",
            "drzava",
            "mesto",
            "zip",
            "ulica",
            "dodatno"
          ]
        }
      ]
    },
    "kontakti": {
      "type": "array",
      "items": [
        {
          "type": "object",
          "properties": {
            "_id": {
              "type": "string"
            },
            "oseba_id": {
              "type": "array",
              "items": [
                {
                  "type": "string"
                },
                {
                  "type": "string"
                },
                {
                  "type": "string"
                }
              ]
            },
            "tip": {
              "type": "string"
            },
            "data": {
              "type": "string"
            }
          },
          "required": [
            "_id",
            "oseba_id",
            "tip",
            "data"
          ]
        }
      ]
    }
  },
  "required": [
    "oseba",
    "naslovi",
    "kontakti"
  ]
}
export const ucenecSporocilaSchema = {
  "type": "array",
  "properties": {
    "sporocilo": {
      "type": "object",
      "properties": {
        "_id": {
          "type": "string"
        },
        "kontakt_posiljatelj_id": {
          "type": "string"
        },
        "kontakt_prejemnik_id": {
          "type": "array",
          "items": [
            {
              "type": "string"
            },
            {
              "type": "string"
            },
            {
              "type": "string"
            },
            {
              "type": "string"
            }
          ]
        },
        "poslano": {
          "type": "string"
        },
        "vsebina": {
          "type": "string"
        }
      },
      "required": [
        "_id",
        "kontakt_posiljatelj_id",
        "kontakt_prejemnik_id",
        "poslano",
        "vsebina"
      ]
    },
    "smer": {
      "type": "string"
    },
    "poslano": {
      "type": "string"
    },
    "posiljatelj": {
      "type": "object",
      "properties": {
        "_id": {
          "type": "string"
        },
        "tip": {
          "type": "array",
          "items": [
            {
              "type": "string"
            }
          ]
        },
        "geslo": {
          "type": "string"
        },
        "letnik": {
          "type": "integer"
        },
        "username": {
          "type": "string"
        },
        "ime": {
          "type": "string"
        },
        "priimek": {
          "type": "string"
        }
      },
      "required": [
        "_id",
        "tip",
        "geslo",
        "letnik",
        "username",
        "ime",
        "priimek"
      ]
    },
    "prejemnik": {
      "type": "object",
      "properties": {
        "_id": {
          "type": "string"
        },
        "tip": {
          "type": "array",
          "items": [
            {
              "type": "string"
            }
          ]
        },
        "geslo": {
          "type": "string"
        },
        "letnik": {
          "type": "integer"
        },
        "username": {
          "type": "string"
        },
        "ime": {
          "type": "string"
        },
        "priimek": {
          "type": "string"
        }
      },
      "required": [
        "_id",
        "tip",
        "geslo",
        "letnik",
        "username",
        "ime",
        "priimek"
      ]
    },
    "posiljatelj_kontakt": {
      "type": "object",
      "properties": {
        "_id": {
          "type": "string"
        },
        "oseba_id": {
          "type": "array",
          "items": [
            {
              "type": "string"
            },
            {
              "type": "string"
            },
            {
              "type": "string"
            }
          ]
        },
        "tip": {
          "type": "string"
        },
        "data": {
          "type": "string"
        }
      },
      "required": [
        "_id",
        "oseba_id",
        "tip",
        "data"
      ]
    },
    "prejemnik_kontakt": {
      "type": "object",
      "properties": {
        "_id": {
          "type": "string"
        },
        "oseba_id": {
          "type": "array",
          "items": [
            {
              "type": "string"
            },
            {
              "type": "string"
            },
            {
              "type": "string"
            }
          ]
        },
        "tip": {
          "type": "string"
        },
        "data": {
          "type": "string"
        }
      },
      "required": [
        "_id",
        "oseba_id",
        "tip",
        "data"
      ]
    }
  },
  "required": [
    "sporocilo",
    "smer",
    "poslano",
    "posiljatelj",
    "prejemnik",
    "posiljatelj_kontakt",
    "prejemnik_kontakt"
  ]
}
export const ucenecTestiSchema = {
  "type": "array",
  "items": [
    {
      "type": "object",
      "properties": {
        "test": {
          "type": "object",
          "properties": {
            "_id": {
              "type": "string"
            },
            "naloga_id": {
              "type": "array",
              "items": [
                {
                  "type": "string"
                }
              ]
            },
            "oseba_admin_id": {
              "type": "array",
              "items": [
                {
                  "type": "string"
                }
              ]
            },
            "oseba_ucenec_id": {
              "type": "array",
              "items": [
                {
                  "type": "string"
                }
              ]
            },
            "deadline": {
              "type": "string"
            },
            "naslov": {
              "type": "string"
            },
            "podnaslov": {
              "type": "string"
            }
          },
          "required": [
            "_id",
            "naloga_id",
            "oseba_admin_id",
            "oseba_ucenec_id",
            "deadline",
            "naslov",
            "podnaslov"
          ]
        },
        "opravljeno": {
          "type": "number"
        },
        "deadline": {
          "type": "object"
        }
      },
      "required": [
        "test",
        "opravljeno",
        "deadline"
      ]
    },
    {
      "type": "object",
      "properties": {
        "test": {
          "type": "object",
          "properties": {
            "_id": {
              "type": "string"
            },
            "naloga_id": {
              "type": "array",
              "items": [
                {
                  "type": "string"
                }
              ]
            },
            "oseba_admin_id": {
              "type": "array",
              "items": [
                {
                  "type": "string"
                }
              ]
            },
            "oseba_ucenec_id": {
              "type": "array",
              "items": [
                {
                  "type": "string"
                }
              ]
            },
            "deadline": {
              "type": "string"
            },
            "naslov": {
              "type": "string"
            },
            "podnaslov": {
              "type": "string"
            }
          },
          "required": [
            "_id",
            "naloga_id",
            "oseba_admin_id",
            "oseba_ucenec_id",
            "deadline",
            "naslov",
            "podnaslov"
          ]
        },
        "opravljeno": {
          "type": "number"
        },
        "deadline": {
          "type": "object"
        }
      },
      "required": [
        "test",
        "opravljeno",
        "deadline"
      ]
    },
    {
      "type": "object",
      "properties": {
        "test": {
          "type": "object",
          "properties": {
            "_id": {
              "type": "string"
            },
            "naloga_id": {
              "type": "array",
              "items": [
                {
                  "type": "string"
                }
              ]
            },
            "oseba_admin_id": {
              "type": "array",
              "items": [
                {
                  "type": "string"
                }
              ]
            },
            "oseba_ucenec_id": {
              "type": "array",
              "items": [
                {
                  "type": "string"
                }
              ]
            },
            "deadline": {
              "type": "string"
            },
            "naslov": {
              "type": "string"
            },
            "podnaslov": {
              "type": "string"
            }
          },
          "required": [
            "_id",
            "naloga_id",
            "oseba_admin_id",
            "oseba_ucenec_id",
            "deadline",
            "naslov",
            "podnaslov"
          ]
        },
        "opravljeno": {
          "type": "number"
        },
        "deadline": {
          "type": "object"
        }
      },
      "required": [
        "test",
        "opravljeno",
        "deadline"
      ]
    },
    {
      "type": "object",
      "properties": {
        "test": {
          "type": "object",
          "properties": {
            "_id": {
              "type": "string"
            },
            "naloga_id": {
              "type": "array",
              "items": [
                {
                  "type": "string"
                }
              ]
            },
            "deadline": {
              "type": "string"
            },
            "naslov": {
              "type": "string"
            },
            "podnaslov": {
              "type": "string"
            }
          },
          "required": [
            "_id",
            "naloga_id",
            "deadline",
            "naslov",
            "podnaslov"
          ]
        },
        "opravljeno": {
          "type": "number"
        },
        "deadline": {
          "type": "object"
        }
      },
      "required": [
        "test",
        "opravljeno",
        "deadline"
      ]
    },
    {
      "type": "object",
      "properties": {
        "test": {
          "type": "object",
          "properties": {
            "_id": {
              "type": "string"
            },
            "naloga_id": {
              "type": "array",
              "items": [
                {
                  "type": "string"
                }
              ]
            },
            "oseba_admin_id": {
              "type": "array",
              "items": [
                {
                  "type": "string"
                }
              ]
            },
            "oseba_ucenec_id": {
              "type": "array",
              "items": [
                {
                  "type": "string"
                }
              ]
            },
            "deadline": {
              "type": "string"
            },
            "naslov": {
              "type": "string"
            },
            "podnaslov": {
              "type": "string"
            }
          },
          "required": [
            "_id",
            "naloga_id",
            "oseba_admin_id",
            "oseba_ucenec_id",
            "deadline",
            "naslov",
            "podnaslov"
          ]
        },
        "opravljeno": {
          "type": "number"
        },
        "deadline": {
          "type": "object"
        }
      },
      "required": [
        "test",
        "opravljeno",
        "deadline"
      ]
    }
  ]
}

export const ucenecNalogaSchema = {

  "type": "object",
  "properties": {
    "_id": {
      "type": "string"
    },
    "tematika_id": {
      "type": "string"
    },
    "resitev": {
      "type": "string"
    },
    "vsebina": {
      "type": "string"
    }
  },
  "required": [
    "_id",
    "tematika_id",
    "resitev",
    "vsebina"
  ]

}

export const ucenecStatusSchema = {
  "type": "object",
  "properties": {
    "_id": {
      "type": "string"
    },
    "naloga_id": {
      "type": "string"
    },
    "test_id": {
      "type": "string"
    },
    "oseba_id": {
      "type": "string"
    },
    "tip": {
      "type": "string"
    },
    "pojasnilo": {
      "type": "string"
    }
  },
  "required": [
    "_id",
    "naloga_id",
    "test_id",
    "oseba_id",
    "tip",
    "pojasnilo"
  ]
}
