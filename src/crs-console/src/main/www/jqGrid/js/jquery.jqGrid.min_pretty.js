var xmlJsonClass = {
    xml2json: function (a, c) {
        if (a.nodeType === 9) a = a.documentElement;
        a = this.toJson(this.toObj(this.removeWhite(a)), a.nodeName, "\t");
        return "{\n" + c + (c ? a.replace(/\t/g, c) : a.replace(/\t|\n/g, "")) + "\n}"
    },
    json2xml: function (a, c) {
        var e = function (g, h, j) {
            var d = "",
                l, k;
            if (g instanceof Array) if (g.length === 0) d += j + "<" + h + ">__EMPTY_ARRAY_</" + h + ">\n";
            else {
                l = 0;
                for (k = g.length; l < k; l += 1) {
                    var m = j + e(g[l], h, j + "\t") + "\n";
                    d += m
                }
            } else if (typeof g === "object") {
                l = false;
                d += j + "<" + h;
                for (k in g) if (g.hasOwnProperty(k)) if (k.charAt(0) === "@") d += " " + k.substr(1) + '="' + g[k].toString() + '"';
                else l = true;
                d += l ? ">" : "/>";
                if (l) {
                    for (k in g) if (g.hasOwnProperty(k)) if (k === "#text") d += g[k];
                    else if (k === "#cdata") d += "<![CDATA[" + g[k] + "]]\>";
                    else if (k.charAt(0) !== "@") d += e(g[k], k, j + "\t");
                    d += (d.charAt(d.length - 1) === "\n" ? j : "") + "</" + h + ">"
                }
            } else d += typeof g === "function" ? j + "<" + h + "><![CDATA[" + g + "]]\></" + h + ">" : g.toString() === '""' || g.toString().length === 0 ? j + "<" + h + ">__EMPTY_STRING_</" + h + ">" : j + "<" + h + ">" + g.toString() + "</" + h + ">";
            return d
        },
            b = "",
            f;
        for (f in a) if (a.hasOwnProperty(f)) b += e(a[f], f, "");
        return c ? b.replace(/\t/g, c) : b.replace(/\t|\n/g, "")
    },
    toObj: function (a) {
        var c = {},
            e = /function/i;
        if (a.nodeType === 1) {
            if (a.attributes.length) {
                var b;
                for (b = 0; b < a.attributes.length; b += 1) c["@" + a.attributes[b].nodeName] = (a.attributes[b].nodeValue || "").toString()
            }
            if (a.firstChild) {
                var f = b = 0,
                    g = false,
                    h;
                for (h = a.firstChild; h; h = h.nextSibling) if (h.nodeType === 1) g = true;
                else if (h.nodeType === 3 && h.nodeValue.match(/[^ \f\n\r\t\v]/)) b += 1;
                else if (h.nodeType === 4) f += 1;
                if (g) if (b < 2 && f < 2) {
                    this.removeWhite(a);
                    for (h =
                    a.firstChild; h; h = h.nextSibling) if (h.nodeType === 3) c["#text"] = this.escape(h.nodeValue);
                    else if (h.nodeType === 4) if (e.test(h.nodeValue)) c[h.nodeName] = [c[h.nodeName], h.nodeValue];
                    else c["#cdata"] = this.escape(h.nodeValue);
                    else if (c[h.nodeName]) if (c[h.nodeName] instanceof Array) c[h.nodeName][c[h.nodeName].length] = this.toObj(h);
                    else c[h.nodeName] = [c[h.nodeName], this.toObj(h)];
                    else c[h.nodeName] = this.toObj(h)
                } else if (a.attributes.length) c["#text"] = this.escape(this.innerXml(a));
                else c = this.escape(this.innerXml(a));
                else if (b) if (a.attributes.length) c["#text"] = this.escape(this.innerXml(a));
                else {
                    c = this.escape(this.innerXml(a));
                    if (c === "__EMPTY_ARRAY_") c = "[]";
                    else if (c === "__EMPTY_STRING_") c = ""
                } else if (f) if (f > 1) c = this.escape(this.innerXml(a));
                else
                for (h = a.firstChild; h; h = h.nextSibling) if (e.test(a.firstChild.nodeValue)) {
                    c = a.firstChild.nodeValue;
                    break
                } else c["#cdata"] = this.escape(h.nodeValue)
            }
            if (!a.attributes.length && !a.firstChild) c = null
        } else if (a.nodeType === 9) c = this.toObj(a.documentElement);
        else alert("unhandled node type: " + a.nodeType);
        return c
    },
    toJson: function (a, c, e) {
        var b = c ? '"' + c + '"' : "";
        if (a === "[]") b += c ? ":[]" : "[]";
        else if (a instanceof Array) {
            var f, g, h = [];
            g = 0;
            for (f = a.length; g < f; g += 1) h[g] = this.toJson(a[g], "", e + "\t");
            b += (c ? ":[" : "[") + (h.length > 1 ? "\n" + e + "\t" + h.join(",\n" + e + "\t") + "\n" + e : h.join("")) + "]"
        } else if (a === null) b += (c && ":") + "null";
        else if (typeof a === "object") {
            f = [];
            for (g in a) if (a.hasOwnProperty(g)) f[f.length] = this.toJson(a[g], g, e + "\t");
            b += (c ? ":{" : "{") + (f.length > 1 ? "\n" + e + "\t" + f.join(",\n" + e + "\t") + "\n" + e : f.join("")) + "}"
        } else if (typeof a === "string") {
            e = /function/i;
            f = a.toString();
            b += /(^-?\d+\.?\d*$)/.test(f) || e.test(f) || f === "false" || f === "true" ? (c && ":") + f : (c && ":") + '"' + a + '"'
        } else b += (c && ":") + a.toString();
        return b
    },
    innerXml: function (a) {
        var c = "";
        if ("innerHTML" in a) c = a.innerHTML;
        else {
            var e = function (b) {
                var f = "",
                    g;
                if (b.nodeType === 1) {
                    f += "<" + b.nodeName;
                    for (g = 0; g < b.attributes.length; g += 1) f += " " + b.attributes[g].nodeName + '="' + (b.attributes[g].nodeValue || "").toString() + '"';
                    if (b.firstChild) {
                        f += ">";
                        for (g = b.firstChild; g; g = g.nextSibling) f += e(g);
                        f += "</" + b.nodeName + ">"
                    } else f += "/>"
                } else if (b.nodeType === 3) f += b.nodeValue;
                else if (b.nodeType === 4) f += "<![CDATA[" + b.nodeValue + "]]\>";
                return f
            };
            for (a = a.firstChild; a; a = a.nextSibling) c += e(a)
        }
        return c
    },
    escape: function (a) {
        return a.replace(/[\\]/g, "\\\\").replace(/[\"]/g, '\\"').replace(/[\n]/g, "\\n").replace(/[\r]/g, "\\r")
    },
    removeWhite: function (a) {
        a.normalize();
        var c;
        for (c = a.firstChild; c;) if (c.nodeType === 3) if (c.nodeValue.match(/[^ \f\n\r\t\v]/)) c = c.nextSibling;
        else {
            var e = c.nextSibling;
            a.removeChild(c);
            c = e
        } else {
            c.nodeType === 1 && this.removeWhite(c);
            c = c.nextSibling
        }
        return a
    }
};
(function (a) {
    a.jgrid = a.jgrid || {};
    a.extend(a.jgrid, {
        htmlDecode: function (c) {
            if (c == "&nbsp;" || c == "&#160;" || c.length == 1 && c.charCodeAt(0) == 160) return "";
            return !c ? c : String(c).replace(/&amp;/g, "&").replace(/&gt;/g, ">").replace(/&lt;/g, "<").replace(/&quot;/g, '"')
        },
        htmlEncode: function (c) {
            return !c ? c : String(c).replace(/&/g, "&amp;").replace(/>/g, "&gt;").replace(/</g, "&lt;").replace(/\"/g, "&quot;")
        },
        format: function (c) {
            var e = a.makeArray(arguments).slice(1);
            if (c === undefined) c = "";
            return c.replace(/\{(\d+)\}/g, function (b, f) {
                return e[f]
            })
        },
        getCellIndex: function (c) {
            c = a(c);
            if (c.is("tr")) return -1;
            c = (!c.is("td") && !c.is("th") ? c.closest("td,th") : c)[0];
            if (a.browser.msie) return a.inArray(c, c.parentNode.cells);
            return c.cellIndex
        },
        stripHtml: function (c) {
            c += "";
            var e = /<("[^"]*"|'[^']*'|[^'">])*>/gi;
            if (c) return (c = c.replace(e, "")) && c !== "&nbsp;" && c !== "&#160;" ? c.replace(/\"/g, "'") : "";
            else
            return c
        },
        stringToDoc: function (c) {
            var e;
            if (typeof c !== "string") return c;
            try {
                e = (new DOMParser).parseFromString(c, "text/xml")
            } catch (b) {
                e = new ActiveXObject("Microsoft.XMLDOM");
                e.async = false;
                e.loadXML(c)
            }
            return e && e.documentElement && e.documentElement.tagName != "parsererror" ? e : null
        },
        parse: function (c) {
            c = c;
            if (c.substr(0, 9) == "while(1);") c = c.substr(9);
            if (c.substr(0, 2) == "/*") c = c.substr(2, c.length - 4);
            c || (c = "{}");
            return a.jgrid.useJSON === true && typeof JSON === "object" && typeof JSON.parse === "function" ? JSON.parse(c) : eval("(" + c + ")")
        },
        parseDate: function (c, e) {
            var b = {
                m: 1,
                d: 1,
                y: 1970,
                h: 0,
                i: 0,
                s: 0
            },
                f, g, h;
            if (e && e !== null && e !== undefined) {
                e = a.trim(e);
                e = e.split(/[\\\/:_;.,\t\T\s-]/);
                c = c.split(/[\\\/:_;.,\t\T\s-]/);
                var j = a.jgrid.formatter.date.monthNames,
                    d = a.jgrid.formatter.date.AmPm,
                    l = function (k, m) {
                        if (k === 0) {
                            if (m == 12) m = 0
                        } else if (m != 12) m += 12;
                        return m
                    };
                f = 0;
                for (g = c.length; f < g; f++) {
                    if (c[f] == "M") {
                        h = a.inArray(e[f], j);
                        if (h !== -1 && h < 12) e[f] = h + 1
                    }
                    if (c[f] == "F") {
                        h = a.inArray(e[f], j);
                        if (h !== -1 && h > 11) e[f] = h + 1 - 12
                    }
                    if (c[f] == "a") {
                        h = a.inArray(e[f], d);
                        if (h !== -1 && h < 2 && e[f] == d[h]) {
                            e[f] = h;
                            b.h = l(e[f], b.h)
                        }
                    }
                    if (c[f] == "A") {
                        h = a.inArray(e[f], d);
                        if (h !== -1 && h > 1 && e[f] == d[h]) {
                            e[f] = h - 2;
                            b.h = l(e[f], b.h)
                        }
                    }
                    if (e[f] !== undefined) b[c[f].toLowerCase()] =
                    parseInt(e[f], 10)
                }
                b.m = parseInt(b.m, 10) - 1;
                c = b.y;
                if (c >= 70 && c <= 99) b.y = 1900 + b.y;
                else if (c >= 0 && c <= 69) b.y = 2E3 + b.y
            }
            return new Date(b.y, b.m, b.d, b.h, b.i, b.s, 0)
        },
        jqID: function (c) {
            c += "";
            return c.replace(/([\.\:\[\]])/g, "\\$1")
        },
        getAccessor: function (c, e) {
            var b, f, g = [],
                h;
            if (typeof e === "function") return e(c);
            b = c[e];
            if (b === undefined) try {
                if (typeof e === "string") g = e.split(".");
                if (h = g.length) for (b = c; b && h--;) {
                    f = g.shift();
                    b = b[f]
                }
            } catch (j) {}
            return b
        },
        ajaxOptions: {},
        from: function (c) {
            return new(function (e, b) {
                if (typeof e == "string") e = a.data(e);
                var f = this,
                    g = e,
                    h = true,
                    j = false,
                    d = b,
                    l = /[\$,%]/g,
                    k = null,
                    m = null,
                    q = false,
                    p = "",
                    u = [],
                    s = true;
                if (typeof e == "object" && e.push) {
                    if (e.length > 0) s = typeof e[0] != "object" ? false : true
                } else
                throw "data provides is not an array";
                this._hasData = function () {
                    return g === null ? false : g.length === 0 ? false : true
                };
                this._getStr = function (n) {
                    var o = [];
                    j && o.push("jQuery.trim(");
                    o.push("String(" + n + ")");
                    j && o.push(")");
                    h || o.push(".toLowerCase()");
                    return o.join("")
                };
                this._strComp = function (n) {
                    return typeof n == "string" ? ".toString()" : ""
                };
                this._group = function (n, o) {
                    return {
                        field: n.toString(),
                        unique: o,
                        items: []
                    }
                };
                this._toStr = function (n) {
                    if (j) n = a.trim(n);
                    h || (n = n.toLowerCase());
                    return n = n.toString().replace(new RegExp('\\"', "g"), '\\"')
                };
                this._funcLoop = function (n) {
                    var o = [];
                    a.each(g, function (r, C) {
                        o.push(n(C))
                    });
                    return o
                };
                this._append = function (n) {
                    if (d === null) d = "";
                    else d += p === "" ? " && " : p;
                    if (q) d += "!";
                    d += "(" + n + ")";
                    q = false;
                    p = ""
                };
                this._setCommand = function (n, o) {
                    k = n;
                    m = o
                };
                this._resetNegate = function () {
                    q = false
                };
                this._repeatCommand = function (n, o) {
                    if (k === null) return f;
                    if (n != null && o != null) return k(n, o);
                    if (m === null) return k(n);
                    if (!s) return k(n);
                    return k(m, n)
                };
                this._equals = function (n, o) {
                    return f._compare(n, o, 1) === 0
                };
                this._compare = function (n, o, r) {
                    if (r === undefined) r = 1;
                    if (n === undefined) n = null;
                    if (o === undefined) o = null;
                    if (n === null && o === null) return 0;
                    if (n === null && o !== null) return 1;
                    if (n !== null && o === null) return -1;
                    if (!h && typeof n !== "number" && typeof o !== "number") {
                        n = String(n).toLowerCase();
                        o = String(o).toLowerCase()
                    }
                    if (n < o) return -r;
                    if (n > o) return r;
                    return 0
                };
                this._performSort =

                function () {
                    if (u.length !== 0) g = f._doSort(g, 0)
                };
                this._doSort = function (n, o) {
                    var r = u[o].by,
                        C = u[o].dir,
                        x = u[o].type,
                        B = u[o].datefmt;
                    if (o == u.length - 1) return f._getOrder(n, r, C, x, B);
                    o++;
                    n = f._getGroup(n, r, C, x, B);
                    r = [];
                    for (C = 0; C < n.length; C++) {
                        x = f._doSort(n[C].items, o);
                        for (B = 0; B < x.length; B++) r.push(x[B])
                    }
                    return r
                };
                this._getOrder = function (n, o, r, C, x) {
                    var B = [],
                        D = [],
                        z = r == "a" ? 1 : -1,
                        y, A;
                    if (C === undefined) C = "text";
                    A = C == "float" || C == "number" || C == "currency" || C == "numeric" ?
                    function (M) {
                        M = parseFloat(String(M).replace(l, ""));
                        return isNaN(M) ? 0 : M
                    } : C == "int" || C == "integer" ?
                    function (M) {
                        return M ? parseFloat(String(M).replace(l, "")) : 0
                    } : C == "date" || C == "datetime" ?
                    function (M) {
                        return a.jgrid.parseDate(x, M).getTime()
                    } : a.isFunction(C) ? C : function (M) {
                        M || (M = "");
                        return a.trim(String(M).toUpperCase())
                    };
                    a.each(n, function (M, U) {
                        y = o !== "" ? a.jgrid.getAccessor(U, o) : U;
                        if (y === undefined) y = "";
                        y = A(y, U);
                        D.push({
                            vSort: y,
                            index: M
                        })
                    });
                    D.sort(function (M, U) {
                        M = M.vSort;
                        U = U.vSort;
                        return f._compare(M, U, z)
                    });
                    C = 0;
                    for (var J = n.length; C < J;) {
                        r = D[C].index;
                        B.push(n[r]);
                        C++
                    }
                    return B
                };
                this._getGroup = function (n, o, r, C, x) {
                    var B = [],
                        D = null,
                        z = null,
                        y;
                    a.each(f._getOrder(n, o, r, C, x), function (A, J) {
                        y = a.jgrid.getAccessor(J, o);
                        if (y === undefined) y = "";
                        if (!f._equals(z, y)) {
                            z = y;
                            D != null && B.push(D);
                            D = f._group(o, y)
                        }
                        D.items.push(J)
                    });
                    D != null && B.push(D);
                    return B
                };
                this.ignoreCase = function () {
                    h = false;
                    return f
                };
                this.useCase = function () {
                    h = true;
                    return f
                };
                this.trim = function () {
                    j = true;
                    return f
                };
                this.noTrim = function () {
                    j = false;
                    return f
                };
                this.combine = function (n) {
                    var o = a.from(g);
                    h || o.ignoreCase();
                    j && o.trim();
                    n = n(o).showQuery();
                    f._append(n);
                    return f
                };
                this.execute = function () {
                    var n = d,
                        o = [];
                    if (n === null) return f;
                    a.each(g, function () {
                        eval(n) && o.push(this)
                    });
                    g = o;
                    return f
                };
                this.data = function () {
                    return g
                };
                this.select = function (n) {
                    f._performSort();
                    if (!f._hasData()) return [];
                    f.execute();
                    if (a.isFunction(n)) {
                        var o = [];
                        a.each(g, function (r, C) {
                            o.push(n(C))
                        });
                        return o
                    }
                    return g
                };
                this.hasMatch = function () {
                    if (!f._hasData()) return false;
                    f.execute();
                    return g.length > 0
                };
                this.showQuery = function (n) {
                    var o = d;
                    if (o === null) o = "no query found";
                    if (a.isFunction(n)) {
                        n(o);
                        return f
                    }
                    return o
                };
                this.andNot = function (n, o, r) {
                    q = !q;
                    return f.and(n, o, r)
                };
                this.orNot = function (n, o, r) {
                    q = !q;
                    return f.or(n, o, r)
                };
                this.not = function (n, o, r) {
                    return f.andNot(n, o, r)
                };
                this.and = function (n, o, r) {
                    p = " && ";
                    if (n === undefined) return f;
                    return f._repeatCommand(n, o, r)
                };
                this.or = function (n, o, r) {
                    p = " || ";
                    if (n === undefined) return f;
                    return f._repeatCommand(n, o, r)
                };
                this.isNot = function (n) {
                    q = !q;
                    return f.is(n)
                };
                this.is = function (n) {
                    f._append("this." + n);
                    f._resetNegate();
                    return f
                };
                this._compareValues = function (n, o, r, C, x) {
                    var B;
                    B = s ? "this." + o : "this";
                    if (r === undefined) r = null;
                    r = r === null ? o : r;
                    switch (x.stype === undefined ? "text" : x.stype) {
                    case "int":
                    case "integer":
                        r = isNaN(Number(r)) ? "0" : r;
                        B = "parseInt(" + B + ",10)";
                        r = "parseInt(" + r + ",10)";
                        break;
                    case "float":
                    case "number":
                    case "numeric":
                        r = String(r).replace(l, "");
                        r = isNaN(Number(r)) ? "0" : r;
                        B = "parseFloat(" + B + ")";
                        r = "parseFloat(" + r + ")";
                        break;
                    case "date":
                    case "datetime":
                        r = String(a.jgrid.parseDate(x.newfmt || "Y-m-d", r).getTime());
                        B = 'jQuery.jgrid.parseDate("' + x.srcfmt + '",' + B + ").getTime()";
                        break;
                    default:
                        B = f._getStr(B);
                        r = f._getStr('"' + f._toStr(r) + '"')
                    }
                    f._append(B + " " + C + " " + r);
                    f._setCommand(n, o);
                    f._resetNegate();
                    return f
                };
                this.equals = function (n, o, r) {
                    return f._compareValues(f.equals, n, o, "==", r)
                };
                this.greater = function (n, o, r) {
                    return f._compareValues(f.greater, n, o, ">", r)
                };
                this.less = function (n, o, r) {
                    return f._compareValues(f.less, n, o, "<", r)
                };
                this.greaterOrEquals = function (n, o, r) {
                    return f._compareValues(f.greaterOrEquals, n, o, ">=", r)
                };
                this.lessOrEquals = function (n, o, r) {
                    return f._compareValues(f.lessOrEquals, n, o, "<=", r)
                };
                this.startsWith = function (n, o) {
                    var r = o === undefined || o === null ? n : o;
                    r = j ? a.trim(r.toString()).length : r.toString().length;
                    if (s) f._append(f._getStr("this." + n) + ".substr(0," + r + ") == " + f._getStr('"' + f._toStr(o) + '"'));
                    else {
                        r = j ? a.trim(o.toString()).length : o.toString().length;
                        f._append(f._getStr("this") + ".substr(0," + r + ") == " + f._getStr('"' + f._toStr(n) + '"'))
                    }
                    f._setCommand(f.startsWith, n);
                    f._resetNegate();
                    return f
                };
                this.endsWith = function (n, o) {
                    var r = o === undefined || o === null ? n : o;
                    r = j ? a.trim(r.toString()).length : r.toString().length;
                    s ? f._append(f._getStr("this." + n) + ".substr(" + f._getStr("this." + n) + ".length-" + r + "," + r + ') == "' + f._toStr(o) + '"') : f._append(f._getStr("this") + ".substr(" + f._getStr("this") + '.length-"' + f._toStr(n) + '".length,"' + f._toStr(n) + '".length) == "' + f._toStr(n) + '"');
                    f._setCommand(f.endsWith, n);
                    f._resetNegate();
                    return f
                };
                this.contains = function (n, o) {
                    s ? f._append(f._getStr("this." + n) + '.indexOf("' + f._toStr(o) + '",0) > -1') : f._append(f._getStr("this") + '.indexOf("' + f._toStr(n) + '",0) > -1');
                    f._setCommand(f.contains, n);
                    f._resetNegate();
                    return f
                };
                this.groupBy = function (n, o, r, C) {
                    if (!f._hasData()) return null;
                    return f._getGroup(g, n, o, r, C)
                };
                this.orderBy = function (n, o, r, C) {
                    o = o === undefined || o === null ? "a" : a.trim(o.toString().toLowerCase());
                    if (r === null || r === undefined) r = "text";
                    if (C === null || C === undefined) C = "Y-m-d";
                    if (o == "desc" || o == "descending") o = "d";
                    if (o == "asc" || o == "ascending") o = "a";
                    u.push({
                        by: n,
                        dir: o,
                        type: r,
                        datefmt: C
                    });
                    return f
                };
                return f
            })(c, null)
        },
        extend: function (c) {
            a.extend(a.fn.jqGrid, c);
            this.no_legacy_api || a.fn.extend(c)
        }
    });
    a.fn.jqGrid = function (c) {
        if (typeof c == "string") {
            var e = a.jgrid.getAccessor(a.fn.jqGrid, c);
            if (!e) throw "jqGrid - No such method: " + c;
            var b = a.makeArray(arguments).slice(1);
            return e.apply(this, b)
        }
        return this.each(function () {
            if (!this.grid) {
                var f = a.extend(true, {
                    url: "",
                    height: 150,
                    page: 1,
                    rowNum: 20,
                    rowTotal: null,
                    records: 0,
                    pager: "",
                    pgbuttons: true,
                    pginput: true,
                    colModel: [],
                    rowList: [],
                    colNames: [],
                    sortorder: "asc",
                    sortname: "",
                    datatype: "xml",
                    mtype: "GET",
                    altRows: false,
                    selarrrow: [],
                    savedRow: [],
                    shrinkToFit: true,
                    xmlReader: {},
                    jsonReader: {},
                    subGrid: false,
                    subGridModel: [],
                    reccount: 0,
                    lastpage: 0,
                    lastsort: 0,
                    selrow: null,
                    beforeSelectRow: null,
                    onSelectRow: null,
                    onSortCol: null,
                    ondblClickRow: null,
                    onRightClickRow: null,
                    onPaging: null,
                    onSelectAll: null,
                    loadComplete: null,
                    gridComplete: null,
                    loadError: null,
                    loadBeforeSend: null,
                    afterInsertRow: null,
                    beforeRequest: null,
                    onHeaderClick: null,
                    viewrecords: false,
                    loadonce: false,
                    multiselect: false,
                    multikey: false,
                    editurl: null,
                    search: false,
                    caption: "",
                    hidegrid: true,
                    hiddengrid: false,
                    postData: {},
                    userData: {},
                    treeGrid: false,
                    treeGridModel: "nested",
                    treeReader: {},
                    treeANode: -1,
                    ExpandColumn: null,
                    tree_root_level: 0,
                    prmNames: {
                        page: "page",
                        rows: "rows",
                        sort: "sidx",
                        order: "sord",
                        search: "_search",
                        nd: "nd",
                        id: "id",
                        oper: "oper",
                        editoper: "edit",
                        addoper: "add",
                        deloper: "del",
                        subgridid: "id",
                        npage: null,
                        totalrows: "totalrows"
                    },
                    forceFit: false,
                    gridstate: "visible",
                    cellEdit: false,
                    cellsubmit: "remote",
                    nv: 0,
                    loadui: "enable",
                    toolbar: [false, ""],
                    scroll: false,
                    multiboxonly: false,
                    deselectAfterSort: true,
                    scrollrows: false,
                    autowidth: false,
                    scrollOffset: 18,
                    cellLayout: 5,
                    subGridWidth: 20,
                    multiselectWidth: 20,
                    gridview: false,
                    rownumWidth: 25,
                    rownumbers: false,
                    pagerpos: "center",
                    recordpos: "right",
                    footerrow: false,
                    userDataOnFooter: false,
                    hoverrows: true,
                    altclass: "ui-priority-secondary",
                    viewsortcols: [false, "vertical", true],
                    resizeclass: "",
                    autoencode: false,
                    remapColumns: [],
                    ajaxGridOptions: {},
                    direction: "ltr",
                    toppager: false,
                    headertitles: false,
                    scrollTimeout: 40,
                    data: [],
                    _index: {},
                    grouping: false,
                    groupingView: {
                        groupField: [],
                        groupOrder: [],
                        groupText: [],
                        groupColumnShow: [],
                        groupSummary: [],
                        showSummaryOnHide: false,
                        sortitems: [],
                        sortnames: [],
                        groupDataSorted: false,
                        summary: [],
                        summaryval: [],
                        plusicon: "ui-icon-circlesmall-plus",
                        minusicon: "ui-icon-circlesmall-minus"
                    },
                    ignoreCase: false,
                    cmTemplate: {}
                }, a.jgrid.defaults, c || {}),
                    g = {
                        headers: [],
                        cols: [],
                        footers: [],
                        dragStart: function (t, v, w) {
                            this.resizing = {
                                idx: t,
                                startX: v.clientX,
                                sOL: w[0]
                            };
                            this.hDiv.style.cursor = "col-resize";
                            this.curGbox = a("#rs_m" + a.jgrid.jqID(f.id), "#gbox_" + a.jgrid.jqID(f.id));
                            this.curGbox.css({
                                display: "block",
                                left: w[0],
                                top: w[1],
                                height: w[2]
                            });
                            a.isFunction(f.resizeStart) && f.resizeStart.call(this, v, t);
                            document.onselectstart = function () {
                                return false
                            }
                        },
                        dragMove: function (t) {
                            if (this.resizing) {
                                var v = t.clientX - this.resizing.startX;
                                t = this.headers[this.resizing.idx];
                                var w = f.direction === "ltr" ? t.width + v : t.width - v,
                                    E;
                                if (w > 33) {
                                    this.curGbox.css({
                                        left: this.resizing.sOL + v
                                    });
                                    if (f.forceFit === true) {
                                        E = this.headers[this.resizing.idx + f.nv];
                                        v = f.direction === "ltr" ? E.width - v : E.width + v;
                                        if (v > 33) {
                                            t.newWidth = w;
                                            E.newWidth = v
                                        }
                                    } else {
                                        this.newWidth = f.direction === "ltr" ? f.tblwidth + v : f.tblwidth - v;
                                        t.newWidth =
                                        w
                                    }
                                }
                            }
                        },
                        dragEnd: function () {
                            this.hDiv.style.cursor = "default";
                            if (this.resizing) {
                                var t = this.resizing.idx,
                                    v = this.headers[t].newWidth || this.headers[t].width;
                                v = parseInt(v, 10);
                                this.resizing = false;
                                a("#rs_m" + a.jgrid.jqID(f.id)).css("display", "none");
                                f.colModel[t].width = v;
                                this.headers[t].width = v;
                                this.headers[t].el.style.width = v + "px";
                                this.cols[t].style.width = v + "px";
                                if (this.footers.length > 0) this.footers[t].style.width = v + "px";
                                if (f.forceFit === true) {
                                    v = this.headers[t + f.nv].newWidth || this.headers[t + f.nv].width;
                                    this.headers[t + f.nv].width = v;
                                    this.headers[t + f.nv].el.style.width = v + "px";
                                    this.cols[t + f.nv].style.width = v + "px";
                                    if (this.footers.length > 0) this.footers[t + f.nv].style.width = v + "px";
                                    f.colModel[t + f.nv].width = v
                                } else {
                                    f.tblwidth = this.newWidth || f.tblwidth;
                                    a("table:first", this.bDiv).css("width", f.tblwidth + "px");
                                    a("table:first", this.hDiv).css("width", f.tblwidth + "px");
                                    this.hDiv.scrollLeft = this.bDiv.scrollLeft;
                                    if (f.footerrow) {
                                        a("table:first", this.sDiv).css("width", f.tblwidth + "px");
                                        this.sDiv.scrollLeft = this.bDiv.scrollLeft
                                    }
                                }
                                a.isFunction(f.resizeStop) && f.resizeStop.call(this, v, t)
                            }
                            this.curGbox = null;
                            document.onselectstart = function () {
                                return true
                            }
                        },
                        populateVisible: function () {
                            g.timer && clearTimeout(g.timer);
                            g.timer = null;
                            var t = a(g.bDiv).height();
                            if (t) {
                                var v = a("table:first", g.bDiv),
                                    w = a("> tbody > tr:gt(0):visible:first", v).outerHeight() || g.prevRowHeight;
                                if (w) {
                                    g.prevRowHeight = w;
                                    var E = f.rowNum,
                                        F = g.scrollTop = g.bDiv.scrollTop,
                                        N = Math.round(v.position().top) - F,
                                        L = N + v.height();
                                    w = w * E;
                                    var T, $, H;
                                    if (L < t && N <= 0 && (f.lastpage === undefined || parseInt((L + F + w - 1) / w, 10) <= f.lastpage)) {
                                        $ =
                                        parseInt((t - L + w - 1) / w, 10);
                                        if (L >= 0 || $ < 2 || f.scroll === true) {
                                            T = Math.round((L + F) / w) + 1;
                                            N = -1
                                        } else N = 1
                                    }
                                    if (N > 0) {
                                        T = parseInt(F / w, 10) + 1;
                                        $ = parseInt((F + t) / w, 10) + 2 - T;
                                        H = true
                                    }
                                    if ($) if (!(f.lastpage && T > f.lastpage || f.lastpage == 1)) if (g.hDiv.loading) g.timer = setTimeout(g.populateVisible, f.scrollTimeout);
                                    else {
                                        f.page = T;
                                        if (H) {
                                            g.selectionPreserver(v[0]);
                                            g.emptyRows(g.bDiv, false)
                                        }
                                        g.populate($)
                                    }
                                }
                            }
                        },
                        scrollGrid: function () {
                            if (f.scroll) {
                                var t = g.bDiv.scrollTop;
                                if (g.scrollTop === undefined) g.scrollTop = 0;
                                if (t != g.scrollTop) {
                                    g.scrollTop =
                                    t;
                                    g.timer && clearTimeout(g.timer);
                                    g.timer = setTimeout(g.populateVisible, f.scrollTimeout)
                                }
                            }
                            g.hDiv.scrollLeft = g.bDiv.scrollLeft;
                            if (f.footerrow) g.sDiv.scrollLeft = g.bDiv.scrollLeft
                        },
                        selectionPreserver: function (t) {
                            var v = t.p,
                                w = v.selrow,
                                E = v.selarrrow ? a.makeArray(v.selarrrow) : null,
                                F = t.grid.bDiv.scrollLeft,
                                N = v.gridComplete;
                            v.gridComplete = function () {
                                v.selrow = null;
                                v.selarrrow = [];
                                if (v.multiselect && E && E.length > 0) for (var L = 0; L < E.length; L++) E[L] != w && a(t).jqGrid("setSelection", E[L], false);
                                w && a(t).jqGrid("setSelection", w, false);
                                t.grid.bDiv.scrollLeft = F;
                                v.gridComplete = N;
                                v.gridComplete && N()
                            }
                        }
                    };
                if (this.tagName != "TABLE") alert("Element is not a table");
                else {
                    a(this).empty();
                    this.p = f;
                    var h, j, d, l;
                    if (this.p.colNames.length === 0) for (h = 0; h < this.p.colModel.length; h++) this.p.colNames[h] = this.p.colModel[h].label || this.p.colModel[h].name;
                    if (this.p.colNames.length !== this.p.colModel.length) alert(a.jgrid.errors.model);
                    else {
                        var k = a("<div class='ui-jqgrid-view'></div>"),
                            m, q = a.browser.msie ? true : false,
                            p = a.browser.safari ? true : false;
                        d =
                        this;
                        d.p.direction = a.trim(d.p.direction.toLowerCase());
                        if (a.inArray(d.p.direction, ["ltr", "rtl"]) == -1) d.p.direction = "ltr";
                        j = d.p.direction;
                        a(k).insertBefore(this);
                        a(this).appendTo(k).removeClass("scroll");
                        var u = a("<div class='ui-jqgrid ui-widget ui-widget-content ui-corner-all'></div>");
                        a(u).insertBefore(k).attr({
                            id: "gbox_" + this.id,
                            dir: j
                        });
                        a(k).appendTo(u).attr("id", "gview_" + this.id);
                        m = q && a.browser.version <= 6 ? '<iframe style="display:block;position:absolute;z-index:-1;filter:Alpha(Opacity=\'0\');" src="javascript:false;"></iframe>' : "";
                        a("<div class='ui-widget-overlay jqgrid-overlay' id='lui_" + this.id + "'></div>").append(m).insertBefore(k);
                        a("<div class='loading ui-state-default ui-state-active' id='load_" + this.id + "'>" + this.p.loadtext + "</div>").insertBefore(k);
                        a(this).attr({
                            cellspacing: "0",
                            cellpadding: "0",
                            border: "0",
                            role: "grid",
                            "aria-multiselectable": !! this.p.multiselect,
                            "aria-labelledby": "gbox_" + this.id
                        });
                        var s = function (t, v) {
                            t = parseInt(t, 10);
                            return isNaN(t) ? v ? v : 0 : t
                        },
                            n = function (t, v, w) {
                                var E = d.p.colModel[t],
                                    F = E.align,
                                    N = 'style="',
                                    L = E.classes,
                                    T = E.name;
                                if (F) N += "text-align:" + F + ";";
                                if (E.hidden === true) N += "display:none;";
                                if (v === 0) N += "width: " + g.headers[t].width + "px;";
                                N += '"' + (L !== undefined ? ' class="' + L + '"' : "") + (E.title && w ? ' title="' + a.jgrid.stripHtml(w) + '"' : "");
                                N += ' aria-describedby="' + d.p.id + "_" + T + '"';
                                return N
                            },
                            o = function (t) {
                                return t === undefined || t === null || t === "" ? "&#160;" : d.p.autoencode ? a.jgrid.htmlEncode(t) : t + ""
                            },
                            r = function (t, v, w, E, F) {
                                var N = d.p.colModel[w];
                                if (typeof N.formatter !== "undefined") {
                                    t = {
                                        rowId: t,
                                        colModel: N,
                                        gid: d.p.id,
                                        pos: w
                                    };
                                    v = a.isFunction(N.formatter) ? N.formatter.call(d, v, t, E, F) : a.fmatter ? a.fn.fmatter(N.formatter, v, t, E, F) : o(v)
                                } else v = o(v);
                                return v
                            },
                            C = function (t, v, w, E, F) {
                                t = r(t, v, w, F, "add");
                                return '<td role="gridcell" ' + n(w, E, t) + ">" + t + "</td>"
                            },
                            x = function (t, v, w) {
                                t = '<input role="checkbox" type="checkbox" id="jqg_' + d.p.id + "_" + t + '" class="cbox" name="jqg_' + d.p.id + "_" + t + '"/>';
                                return '<td role="gridcell" ' + n(v, w, "") + ">" + t + "</td>"
                            },
                            B = function (t, v, w, E) {
                                w = (parseInt(w, 10) - 1) * parseInt(E, 10) + 1 + v;
                                return '<td role="gridcell" class="ui-state-default jqgrid-rownum" ' + n(t, v, "") + ">" + w + "</td>"
                            },
                            D = function (t) {
                                var v, w = [],
                                    E = 0,
                                    F;
                                for (F = 0; F < d.p.colModel.length; F++) {
                                    v = d.p.colModel[F];
                                    if (v.name !== "cb" && v.name !== "subgrid" && v.name !== "rn") {
                                        w[E] = t == "local" ? v.name : t == "xml" ? v.xmlmap || v.name : v.jsonmap || v.name;
                                        E++
                                    }
                                }
                                return w
                            },
                            z = function (t) {
                                var v = d.p.remapColumns;
                                if (!v || !v.length) v = a.map(d.p.colModel, function (w, E) {
                                    return E
                                });
                                if (t) v = a.map(v, function (w) {
                                    return w < t ? null : w - t
                                });
                                return v
                            },
                            y = function (t, v) {
                                if (d.p.deepempty) a("#" + a.jgrid.jqID(d.p.id) + " tbody:first tr:gt(0)").remove();
                                else {
                                    var w =
                                    a("#" + a.jgrid.jqID(d.p.id) + " tbody:first tr:first")[0];
                                    a("#" + a.jgrid.jqID(d.p.id) + " tbody:first").empty().append(w)
                                }
                                if (v && d.p.scroll) {
                                    a(">div:first", t).css({
                                        height: "auto"
                                    }).children("div:first").css({
                                        height: 0,
                                        display: "none"
                                    });
                                    t.scrollTop = 0
                                }
                            },
                            A = function () {
                                var t = d.p.data.length,
                                    v, w, E;
                                v = d.p.rownumbers === true ? 1 : 0;
                                w = d.p.multiselect === true ? 1 : 0;
                                E = d.p.subGrid === true ? 1 : 0;
                                v = d.p.keyIndex === false || d.p.loadonce === true ? d.p.localReader.id : d.p.colModel[d.p.keyIndex + w + E + v].name;
                                for (w = 0; w < t; w++) {
                                    E = a.jgrid.getAccessor(d.p.data[w], v);
                                    d.p._index[E] = w
                                }
                            },
                            J = function (t, v, w, E, F) {
                                var N = new Date,
                                    L = d.p.datatype != "local" && d.p.loadonce || d.p.datatype == "xmlstring",
                                    T, $ = d.p.datatype == "local" ? "local" : "xml";
                                if (L) {
                                    d.p.data = [];
                                    d.p._index = {};
                                    d.p.localReader.id = T = "_id_"
                                }
                                d.p.reccount = 0;
                                if (a.isXMLDoc(t)) {
                                    if (d.p.treeANode === -1 && !d.p.scroll) {
                                        y(v, false);
                                        w = 1
                                    } else w = w > 1 ? w : 1;
                                    var H, K = 0,
                                        O, X, fa = 0,
                                        ea = 0,
                                        Y = 0,
                                        ka, R = [],
                                        ra, na = {},
                                        ja, ha, sa = [],
                                        Ea = d.p.altRows === true ? " " + d.p.altclass : "";
                                    d.p.xmlReader.repeatitems || (R = D($));
                                    ka = d.p.keyIndex === false ? d.p.xmlReader.id : d.p.keyIndex;
                                    if (R.length > 0 && !isNaN(ka)) {
                                        if (d.p.remapColumns && d.p.remapColumns.length) ka = a.inArray(ka, d.p.remapColumns);
                                        ka = R[ka]
                                    }
                                    $ = (ka + "").indexOf("[") === -1 ? R.length ?
                                    function (ya, ua) {
                                        return a(ka, ya).text() || ua
                                    } : function (ya, ua) {
                                        return a(d.p.xmlReader.cell, ya).eq(ka).text() || ua
                                    } : function (ya, ua) {
                                        return ya.getAttribute(ka.replace(/[\[\]]/g, "")) || ua
                                    };
                                    d.p.userData = {};
                                    a(d.p.xmlReader.page, t).each(function () {
                                        d.p.page = this.textContent || this.text || 0
                                    });
                                    a(d.p.xmlReader.total, t).each(function () {
                                        d.p.lastpage = this.textContent || this.text;
                                        if (d.p.lastpage === undefined) d.p.lastpage = 1
                                    });
                                    a(d.p.xmlReader.records, t).each(function () {
                                        d.p.records = this.textContent || this.text || 0
                                    });
                                    a(d.p.xmlReader.userdata, t).each(function () {
                                        d.p.userData[this.getAttribute("name")] = this.textContent || this.text
                                    });
                                    (t = a(d.p.xmlReader.root + " " + d.p.xmlReader.row, t)) || (t = []);
                                    var Aa = t.length,
                                        va = 0;
                                    if (t && Aa) {
                                        var Ca = parseInt(d.p.rowNum, 10),
                                            Ha = d.p.scroll ? (parseInt(d.p.page, 10) - 1) * Ca + 1 : 1;
                                        if (F) Ca *= F + 1;
                                        F = a.isFunction(d.p.afterInsertRow);
                                        var Da = {},
                                            Ia = "";
                                        if (d.p.grouping && d.p.groupingView.groupCollapse === true) Ia = ' style="display:none;"';
                                        for (; va < Aa;) {
                                            ja = t[va];
                                            ha = $(ja, Ha + va);
                                            H = w === 0 ? 0 : w + 1;
                                            H = (H + va) % 2 == 1 ? Ea : "";
                                            sa.push("<tr" + Ia + ' id="' + ha + '" role="row" class ="ui-widget-content jqgrow ui-row-' + d.p.direction + "" + H + '">');
                                            if (d.p.rownumbers === true) {
                                                sa.push(B(0, va, d.p.page, d.p.rowNum));
                                                Y = 1
                                            }
                                            if (d.p.multiselect === true) {
                                                sa.push(x(ha, Y, va));
                                                fa = 1
                                            }
                                            if (d.p.subGrid === true) {
                                                sa.push(a(d).jqGrid("addSubGridCell", fa + Y, va + w));
                                                ea = 1
                                            }
                                            if (d.p.xmlReader.repeatitems) {
                                                ra || (ra = z(fa + ea + Y));
                                                var Ka = a(d.p.xmlReader.cell, ja);
                                                a.each(ra, function (ya) {
                                                    var ua = Ka[this];
                                                    if (!ua) return false;
                                                    O = ua.textContent || ua.text;
                                                    na[d.p.colModel[ya + fa + ea + Y].name] = O;
                                                    sa.push(C(ha, O, ya + fa + ea + Y, va + w, ja))
                                                })
                                            } else
                                            for (H = 0; H < R.length; H++) {
                                                O = a(R[H], ja).text();
                                                na[d.p.colModel[H + fa + ea + Y].name] = O;
                                                sa.push(C(ha, O, H + fa + ea + Y, va + w, ja))
                                            }
                                            sa.push("</tr>");
                                            if (d.p.grouping) {
                                                H = d.p.groupingView.groupField.length;
                                                X = [];
                                                for (var Ja = 0; Ja < H; Ja++) X.push(na[d.p.groupingView.groupField[Ja]]);
                                                Da = a(d).jqGrid("groupingPrepare", sa, X, Da, na);
                                                sa = []
                                            }
                                            if (L) {
                                                na[T] = ha;
                                                d.p.data.push(na)
                                            }
                                            if (d.p.gridview === false) {
                                                if (d.p.treeGrid === true) {
                                                    H = d.p.treeANode > -1 ? d.p.treeANode : 0;
                                                    X = a(sa.join(""))[0];
                                                    a(d.rows[va + H]).after(X);
                                                    try {
                                                        a(d).jqGrid("setTreeNode", na, X)
                                                    } catch (Ma) {}
                                                } else a("tbody:first", v).append(sa.join(""));
                                                if (d.p.subGrid === true) try {
                                                    a(d).jqGrid("addSubGrid", d.rows[d.rows.length - 1], fa + Y)
                                                } catch (Na) {}
                                                F && d.p.afterInsertRow.call(d, ha, na, ja);
                                                sa = []
                                            }
                                            na = {};
                                            K++;
                                            va++;
                                            if (K == Ca) break
                                        }
                                    }
                                    if (d.p.gridview === true) if (d.p.grouping) {
                                        a(d).jqGrid("groupingRender", Da, d.p.colModel.length);
                                        Da = null
                                    } else a("tbody:first", v).append(sa.join(""));
                                    d.p.totaltime = new Date - N;
                                    if (K > 0) if (d.p.records === 0) d.p.records = Aa;
                                    sa = null;
                                    if (!d.p.treeGrid && !d.p.scroll) d.grid.bDiv.scrollTop = 0;
                                    d.p.reccount = K;
                                    d.p.treeANode = -1;
                                    d.p.userDataOnFooter && a(d).jqGrid("footerData", "set", d.p.userData, true);
                                    if (L) {
                                        d.p.records = Aa;
                                        d.p.lastpage = Math.ceil(Aa / Ca)
                                    }
                                    E || d.updatepager(false, true);
                                    if (L) {
                                        for (; K < Aa;) {
                                            ja = t[K];
                                            ha = $(ja, K);
                                            if (d.p.xmlReader.repeatitems) {
                                                ra || (ra = z(fa + ea + Y));
                                                var La = a(d.p.xmlReader.cell, ja);
                                                a.each(ra, function (ya) {
                                                    var ua = La[this];
                                                    if (!ua) return false;
                                                    O = ua.textContent || ua.text;
                                                    na[d.p.colModel[ya + fa + ea + Y].name] = O
                                                })
                                            } else
                                            for (H = 0; H < R.length; H++) {
                                                O = a(R[H], ja).text();
                                                na[d.p.colModel[H + fa + ea + Y].name] = O
                                            }
                                            na[T] = ha;
                                            d.p.data.push(na);
                                            na = {};
                                            K++
                                        }
                                        A()
                                    }
                                }
                            },
                            M = function (t, v, w, E, F) {
                                var N = new Date;
                                if (t) {
                                    if (d.p.treeANode === -1 && !d.p.scroll) {
                                        y(v, false);
                                        w = 1
                                    } else w = w > 1 ? w : 1;
                                    var L, T, $ = d.p.datatype != "local" && d.p.loadonce || d.p.datatype == "jsonstring";
                                    if ($) {
                                        d.p.data = [];
                                        d.p._index = {};
                                        L = d.p.localReader.id = "_id_"
                                    }
                                    d.p.reccount = 0;
                                    if (d.p.datatype == "local") {
                                        v = d.p.localReader;
                                        T = "local"
                                    } else {
                                        v = d.p.jsonReader;
                                        T = "json"
                                    }
                                    var H = 0,
                                        K, O, X, fa = [],
                                        ea, Y = 0,
                                        ka = 0,
                                        R = 0,
                                        ra, na, ja = {},
                                        ha;
                                    X = [];
                                    var sa = d.p.altRows === true ? " " + d.p.altclass : "";
                                    d.p.page = a.jgrid.getAccessor(t, v.page) || 0;
                                    ra = a.jgrid.getAccessor(t, v.total);
                                    d.p.lastpage = ra === undefined ? 1 : ra;
                                    d.p.records = a.jgrid.getAccessor(t, v.records) || 0;
                                    d.p.userData = a.jgrid.getAccessor(t, v.userdata) || {};
                                    v.repeatitems || (ea = fa = D(T));
                                    T = d.p.keyIndex === false ? v.id : d.p.keyIndex;
                                    if (fa.length > 0 && !isNaN(T)) {
                                        if (d.p.remapColumns && d.p.remapColumns.length) T = a.inArray(T, d.p.remapColumns);
                                        T = fa[T]
                                    }(na =
                                    a.jgrid.getAccessor(t, v.root)) || (na = []);
                                    ra = na.length;
                                    t = 0;
                                    var Ea = parseInt(d.p.rowNum, 10),
                                        Aa = d.p.scroll ? (parseInt(d.p.page, 10) - 1) * Ea + 1 : 1;
                                    if (F) Ea *= F + 1;
                                    var va = a.isFunction(d.p.afterInsertRow),
                                        Ca = {},
                                        Ha = "";
                                    if (d.p.grouping && d.p.groupingView.groupCollapse === true) Ha = ' style="display:none;"';
                                    for (; t < ra;) {
                                        F = na[t];
                                        ha = a.jgrid.getAccessor(F, T);
                                        if (ha === undefined) {
                                            ha = Aa + t;
                                            if (fa.length === 0) if (v.cell) ha = F[v.cell][T] || ha
                                        }
                                        K = w === 1 ? 0 : w;
                                        K = (K + t) % 2 == 1 ? sa : "";
                                        X.push("<tr" + Ha + ' id="' + ha + '" role="row" class= "ui-widget-content jqgrow ui-row-' + d.p.direction + "" + K + '">');
                                        if (d.p.rownumbers === true) {
                                            X.push(B(0, t, d.p.page, d.p.rowNum));
                                            R = 1
                                        }
                                        if (d.p.multiselect) {
                                            X.push(x(ha, R, t));
                                            Y = 1
                                        }
                                        if (d.p.subGrid) {
                                            X.push(a(d).jqGrid("addSubGridCell", Y + R, t + w));
                                            ka = 1
                                        }
                                        if (v.repeatitems) {
                                            if (v.cell) F = a.jgrid.getAccessor(F, v.cell);
                                            ea || (ea = z(Y + ka + R))
                                        }
                                        for (O = 0; O < ea.length; O++) {
                                            K = a.jgrid.getAccessor(F, ea[O]);
                                            X.push(C(ha, K, O + Y + ka + R, t + w, F));
                                            ja[d.p.colModel[O + Y + ka + R].name] = K
                                        }
                                        X.push("</tr>");
                                        if (d.p.grouping) {
                                            K = d.p.groupingView.groupField.length;
                                            O = [];
                                            for (var Da = 0; Da < K; Da++) O.push(ja[d.p.groupingView.groupField[Da]]);
                                            Ca = a(d).jqGrid("groupingPrepare", X, O, Ca, ja);
                                            X = []
                                        }
                                        if ($) {
                                            ja[L] = ha;
                                            d.p.data.push(ja)
                                        }
                                        if (d.p.gridview === false) {
                                            if (d.p.treeGrid === true) {
                                                K = d.p.treeANode > -1 ? d.p.treeANode : 0;
                                                X = a(X.join(""))[0];
                                                a(d.rows[t + K]).after(X);
                                                try {
                                                    a(d).jqGrid("setTreeNode", ja, X)
                                                } catch (Ia) {}
                                            } else a("#" + a.jgrid.jqID(d.p.id) + " tbody:first").append(X.join(""));
                                            if (d.p.subGrid === true) try {
                                                a(d).jqGrid("addSubGrid", d.rows[d.rows.length - 1], Y + R)
                                            } catch (Ka) {}
                                            va && d.p.afterInsertRow.call(d, ha, ja, F);
                                            X = []
                                        }
                                        ja = {};
                                        H++;
                                        t++;
                                        if (H == Ea) break
                                    }
                                    if (d.p.gridview === true) d.p.grouping ? a(d).jqGrid("groupingRender", Ca, d.p.colModel.length) : a("#" + a.jgrid.jqID(d.p.id) + " tbody:first").append(X.join(""));
                                    d.p.totaltime = new Date - N;
                                    if (H > 0) if (d.p.records === 0) d.p.records = ra;
                                    if (!d.p.treeGrid && !d.p.scroll) d.grid.bDiv.scrollTop = 0;
                                    d.p.reccount = H;
                                    d.p.treeANode = -1;
                                    d.p.userDataOnFooter && a(d).jqGrid("footerData", "set", d.p.userData, true);
                                    if ($) {
                                        d.p.records = ra;
                                        d.p.lastpage = Math.ceil(ra / Ea)
                                    }
                                    E || d.updatepager(false, true);
                                    if ($) {
                                        for (; H < ra;) {
                                            F = na[H];
                                            ha = a.jgrid.getAccessor(F, T);
                                            if (ha === undefined) {
                                                ha =
                                                Aa + H;
                                                if (fa.length === 0) if (v.cell) ha = F[v.cell][T] || ha
                                            }
                                            if (F) {
                                                if (v.repeatitems) {
                                                    if (v.cell) F = a.jgrid.getAccessor(F, v.cell);
                                                    ea || (ea = z(Y + ka + R))
                                                }
                                                for (O = 0; O < ea.length; O++) {
                                                    K = a.jgrid.getAccessor(F, ea[O]);
                                                    ja[d.p.colModel[O + Y + ka + R].name] = K
                                                }
                                                ja[L] = ha;
                                                d.p.data.push(ja);
                                                ja = {}
                                            }
                                            H++
                                        }
                                        A()
                                    }
                                }
                            },
                            U = function () {
                                var t, v = false,
                                    w = {},
                                    E = [],
                                    F = [],
                                    N, L, T;
                                if (a.isArray(d.p.data)) {
                                    var $ = d.p.grouping ? d.p.groupingView : false;
                                    a.each(d.p.colModel, function () {
                                        L = this.sorttype || "text";
                                        if (L == "date" || L == "datetime") {
                                            if (this.formatter && typeof this.formatter === "string" && this.formatter == "date") {
                                                N = this.formatoptions && this.formatoptions.srcformat ? this.formatoptions.srcformat : a.jgrid.formatter.date.srcformat;
                                                T = this.formatoptions && this.formatoptions.newformat ? this.formatoptions.newformat : a.jgrid.formatter.date.newformat
                                            } else N = T = this.datefmt || "Y-m-d";
                                            w[this.name] = {
                                                stype: L,
                                                srcfmt: N,
                                                newfmt: T
                                            }
                                        } else w[this.name] = {
                                            stype: L,
                                            srcfmt: "",
                                            newfmt: ""
                                        };
                                        if (d.p.grouping && this.name == $.groupField[0]) {
                                            var R = this.name;
                                            if (typeof this.index != "undefined") R = this.index;
                                            E[0] = w[R];
                                            F.push(R)
                                        }
                                        if (!v && (this.index == d.p.sortname || this.name == d.p.sortname)) {
                                            t = this.name;
                                            v = true
                                        }
                                    });
                                    if (d.p.treeGrid) a(d).jqGrid("SortTree", t, d.p.sortorder, w[t].stype, w[t].srcfmt);
                                    else {
                                        var H = {
											 eq : function (R) { return R.equals },
											 ne : function (R) { return R.not().equals },
											 lt : function (R) { return R.less },
											 le : function (R) { return R.lessOrEquals },
											 gt : function (R) { return R.greater },
											 ge : function (R) { return R.greaterOrEquals },
											 cn : function (R) { return R.contains },
											 nc : function (R) { return R.not().contains },
											 bw : function (R) { return R.startsWith },
											 bn : function (R) { return R.not().startsWith },
											 en : function (R) { return R.not().endsWith },
											 ew : function (R) { return R.endsWith },
											 ni : function (R) { return R.not().equals },
											"in": function (R) { return R.equals }
                                        },
                                            K = a.jgrid.from(d.p.data);
                                        if (d.p.ignoreCase) K = K.ignoreCase();
                                        if (d.p.search === true) {
                                            var O = d.p.postData.filters,
                                                X;
                                            if (O) {
                                                if (typeof O == "string") O = a.jgrid.parse(O);
                                                for (var fa = 0, ea = O.rules.length, Y; fa < ea; fa++) {
                                                    Y = O.rules[fa];
                                                    X = O.groupOp;
                                                    if (H[Y.op] && Y.field && Y.data && X) K = X.toUpperCase() == "OR" ? H[Y.op](K)(Y.field, Y.data, w[Y.field]).or() : H[Y.op](K)(Y.field, Y.data, w[Y.field])
                                                }
                                            } else
                                            try {
                                                K = H[d.p.postData.searchOper](K)(d.p.postData.searchField, d.p.postData.searchString, w[d.p.postData.searchField])
                                            } catch (ka) {}
                                        }
                                        if (d.p.grouping) {
                                            K.orderBy(F, $.groupOrder[0], E[0].stype, E[0].srcfmt);
                                            $.groupDataSorted = true
                                        }
                                        if (t && d.p.sortorder && v) d.p.sortorder.toUpperCase() == "DESC" ? K.orderBy(d.p.sortname, "d", w[t].stype, w[t].srcfmt) : K.orderBy(d.p.sortname, "a", w[t].stype, w[t].srcfmt);
                                        H = K.select();
                                        K = parseInt(d.p.rowNum, 10);
                                        O = H.length;
                                        X = parseInt(d.p.page, 10);
                                        fa = Math.ceil(O / K);
                                        ea = {};
                                        H = H.slice((X - 1) * K, X * K);
                                        w = K = null;
                                        ea[d.p.localReader.total] = fa;
                                        ea[d.p.localReader.page] = X;
                                        ea[d.p.localReader.records] = O;
                                        ea[d.p.localReader.root] = H;
                                        H = null;
                                        return ea
                                    }
                                }
                            },
                            ba = function () {
                                d.grid.hDiv.loading = true;
                                if (!d.p.hiddengrid) switch (d.p.loadui) {
                                case "disable":
                                    break;
                                case "enable":
                                    a("#load_" + a.jgrid.jqID(d.p.id)).show();
                                    break;
                                case "block":
                                    a("#lui_" + a.jgrid.jqID(d.p.id)).show();
                                    a("#load_" + a.jgrid.jqID(d.p.id)).show();
                                    break
                                }
                            },
                            W = function () {
                                d.grid.hDiv.loading = false;
                                switch (d.p.loadui) {
                                case "disable":
                                    break;
                                case "enable":
                                    a("#load_" + a.jgrid.jqID(d.p.id)).hide();
                                    break;
                                case "block":
                                    a("#lui_" + a.jgrid.jqID(d.p.id)).hide();
                                    a("#load_" + a.jgrid.jqID(d.p.id)).hide();
                                    break
                                }
                            },
                            ca = function (t) {
                                if (!d.grid.hDiv.loading) {
                                    var v = d.p.scroll && t === false,
                                        w = {},
                                        E, F = d.p.prmNames;
                                    if (d.p.page <= 0) d.p.page = 1;
                                    if (F.search !== null) w[F.search] = d.p.search;
                                    if (F.nd !== null) w[F.nd] = (new Date).getTime();
                                    if (F.rows !== null) w[F.rows] = d.p.rowNum;
                                    if (F.page !== null) w[F.page] = d.p.page;
                                    if (F.sort !== null) w[F.sort] = d.p.sortname;
                                    if (F.order !== null) w[F.order] = d.p.sortorder;
                                    if (d.p.rowTotal !== null && F.totalrows !== null) w[F.totalrows] = d.p.rowTotal;
                                    var N = d.p.loadComplete,
                                        L = a.isFunction(N);
                                    L || (N = null);
                                    var T = 0;
                                    t = t || 1;
                                    if (t > 1) if (F.npage !== null) {
                                        w[F.npage] = t;
                                        T = t - 1;
                                        t = 1
                                    } else N = function (H) {
                                        d.p.page++;
                                        d.grid.hDiv.loading = false;
                                        L && d.p.loadComplete.call(d, H);
                                        ca(t - 1)
                                    };
                                    else F.npage !== null && delete d.p.postData[F.npage];
                                    if (d.p.grouping) {
                                        a(d).jqGrid("groupingSetup");
                                        if (d.p.groupingView.groupDataSorted === true) w[F.sort] = d.p.groupingView.groupField[0] + " " + d.p.groupingView.groupOrder[0] + ", " + w[F.sort]
                                    }
                                    a.extend(d.p.postData, w);
                                    var $ = !d.p.scroll ? 1 : d.rows.length - 1;
                                    if (a.isFunction(d.p.datatype)) d.p.datatype.call(d, d.p.postData, "load_" + d.p.id);
                                    else {
                                        a.isFunction(d.p.beforeRequest) && d.p.beforeRequest.call(d);
                                        E = d.p.datatype.toLowerCase();
                                        switch (E) {
                                        case "json":
                                        case "jsonp":
                                        case "xml":
                                        case "script":
                                            a.ajax(a.extend({
                                                url: d.p.url,
                                                type: d.p.mtype,
                                                dataType: E,
                                                data: a.isFunction(d.p.serializeGridData) ? d.p.serializeGridData.call(d, d.p.postData) : d.p.postData,
                                                success: function (H) {
                                                    E === "xml" ? J(H, d.grid.bDiv, $, t > 1, T) : M(H, d.grid.bDiv, $, t > 1, T);
                                                    N && N.call(d, H);
                                                    v && d.grid.populateVisible();
                                                    if (d.p.loadonce || d.p.treeGrid) d.p.datatype = "local";
                                                    W()
                                                },
                                                error: function (H, K, O) {
                                                    a.isFunction(d.p.loadError) && d.p.loadError.call(d, H, K, O);
                                                    W()
                                                },
                                                beforeSend: function (H) {
                                                    ba();
                                                    a.isFunction(d.p.loadBeforeSend) && d.p.loadBeforeSend.call(d, H)
                                                }
                                            }, a.jgrid.ajaxOptions, d.p.ajaxGridOptions));
                                            break;
                                        case "xmlstring":
                                            ba();
                                            w = a.jgrid.stringToDoc(d.p.datastr);
                                            J(w, d.grid.bDiv);
                                            L && d.p.loadComplete.call(d, w);
                                            d.p.datatype = "local";
                                            d.p.datastr = null;
                                            W();
                                            break;
                                        case "jsonstring":
                                            ba();
                                            w = typeof d.p.datastr == "string" ? a.jgrid.parse(d.p.datastr) : d.p.datastr;
                                            M(w, d.grid.bDiv);
                                            L && d.p.loadComplete.call(d, w);
                                            d.p.datatype = "local";
                                            d.p.datastr = null;
                                            W();
                                            break;
                                        case "local":
                                        case "clientside":
                                            ba();
                                            d.p.datatype = "local";
                                            w = U();
                                            M(w, d.grid.bDiv, $, t > 1, T);
                                            N && N.call(d, w);
                                            v && d.grid.populateVisible();
                                            W();
                                            break
                                        }
                                    }
                                }
                            };
                        m = function (t, v) {
                            var w = "",
                                E = "<table cellspacing='0' cellpadding='0' border='0' style='table-layout:auto;' class='ui-pg-table'><tbody><tr>",
                                F = "",
                                N, L, T, $, H = function (K) {
                                    var O;
                                    if (a.isFunction(d.p.onPaging)) O = d.p.onPaging.call(d, K);
                                    d.p.selrow = null;
                                    if (d.p.multiselect) {
                                        d.p.selarrrow = [];
                                        a("#cb_" + a.jgrid.jqID(d.p.id), d.grid.hDiv).attr("checked", false)
                                    }
                                    d.p.savedRow = [];
                                    if (O == "stop") return false;
                                    return true
                                };
                            t = t.substr(1);
                            N = "pg_" + t;
                            L = t + "_left";
                            T = t + "_center";
                            $ = t + "_right";
                            a("#" + t).append("<div id='" + N + "' class='ui-pager-control' role='group'><table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table' style='width:100%;table-layout:fixed;height:100%;' role='row'><tbody><tr><td id='" + L + "' align='left'></td><td id='" + T + "' align='center' style='white-space:pre;'></td><td id='" + $ + "' align='right'></td></tr></tbody></table></div>").attr("dir", "ltr");
                            if (d.p.rowList.length > 0) {
                                F = "<td dir='" + j + "'>";
                                F += "<select class='ui-pg-selbox' role='listbox'>";
                                for (L = 0; L < d.p.rowList.length; L++) F += '<option role="option" value="' + d.p.rowList[L] + '"' + (d.p.rowNum == d.p.rowList[L] ? ' selected="selected"' : "") + ">" + d.p.rowList[L] + "</option>";
                                F += "</select></td>"
                            }
                            if (j == "rtl") E += F;
                            if (d.p.pginput === true) w = "<td dir='" + j + "'>" + a.jgrid.format(d.p.pgtext || "", "<input class='ui-pg-input' type='text' size='2' maxlength='7' value='0' role='textbox'/>", "<span id='sp_1'></span>") + "</td>";
                            if (d.p.pgbuttons === true) {
                                L = ["first" + v, "prev" + v, "next" + v, "last" + v];
                                j == "rtl" && L.reverse();
                                E += "<td id='" + L[0] + "' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-first'></span></td>";
                                E += "<td id='" + L[1] + "' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-prev'></span></td>";
                                E += w !== "" ? "<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>" + w + "<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>" : "";
                                E += "<td id='" + L[2] + "' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-next'></span></td>";
                                E += "<td id='" + L[3] + "' class='ui-pg-button ui-corner-all'><span class='ui-icon ui-icon-seek-end'></span></td>"
                            } else if (w !== "") E += w;
                            if (j == "ltr") E += F;
                            E += "</tr></tbody></table>";
                            d.p.viewrecords === true && a("td#" + t + "_" + d.p.recordpos, "#" + N).append("<div dir='" + j + "' style='text-align:" + d.p.recordpos + "' class='ui-paging-info'></div>");
                            a("td#" + t + "_" + d.p.pagerpos, "#" + N).append(E);
                            F = a(".ui-jqgrid").css("font-size") || "11px";
                            a(document.body).append("<div id='testpg' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:" + F + ";visibility:hidden;' ></div>");
                            E = a(E).clone().appendTo("#testpg").width();
                            a("#testpg").remove();
                            if (E > 0) {
                                if (w != "") E += 50;
                                a("td#" + t + "_" + d.p.pagerpos, "#" + N).width(E)
                            }
                            d.p._nvtd = [];
                            d.p._nvtd[0] = E ? Math.floor((d.p.width - E) / 2) : Math.floor(d.p.width / 3);
                            d.p._nvtd[1] = 0;
                            E = null;
                            a(".ui-pg-selbox", "#" + N).bind("change", function () {
                                d.p.page = Math.round(d.p.rowNum * (d.p.page - 1) / this.value - 0.5) + 1;
                                d.p.rowNum =
                                this.value;
                                if (v) a(".ui-pg-selbox", d.p.pager).val(this.value);
                                else d.p.toppager && a(".ui-pg-selbox", d.p.toppager).val(this.value);
                                if (!H("records")) return false;
                                ca();
                                return false
                            });
                            if (d.p.pgbuttons === true) {
                                a(".ui-pg-button", "#" + N).hover(function () {
                                    if (a(this).hasClass("ui-state-disabled")) this.style.cursor = "default";
                                    else {
                                        a(this).addClass("ui-state-hover");
                                        this.style.cursor = "pointer"
                                    }
                                }, function () {
                                    if (!a(this).hasClass("ui-state-disabled")) {
                                        a(this).removeClass("ui-state-hover");
                                        this.style.cursor = "default"
                                    }
                                });
                                a("#first" + v + ", #prev" + v + ", #next" + v + ", #last" + v, "#" + t).click(function () {
                                    var K = s(d.p.page, 1),
                                        O = s(d.p.lastpage, 1),
                                        X = false,
                                        fa = true,
                                        ea = true,
                                        Y = true,
                                        ka = true;
                                    if (O === 0 || O === 1) ka = Y = ea = fa = false;
                                    else if (O > 1 && K >= 1) if (K === 1) ea = fa = false;
                                    else {
                                        if (!(K > 1 && K < O)) if (K === O) ka = Y = false
                                    } else if (O > 1 && K === 0) {
                                        ka = Y = false;
                                        K = O - 1
                                    }
                                    if (this.id === "first" + v && fa) {
                                        d.p.page = 1;
                                        X = true
                                    }
                                    if (this.id === "prev" + v && ea) {
                                        d.p.page = K - 1;
                                        X = true
                                    }
                                    if (this.id === "next" + v && Y) {
                                        d.p.page = K + 1;
                                        X = true
                                    }
                                    if (this.id === "last" + v && ka) {
                                        d.p.page = O;
                                        X = true
                                    }
                                    if (X) {
                                        if (!H(this.id)) return false;
                                        ca()
                                    }
                                    return false
                                })
                            }
                            d.p.pginput === true && a("input.ui-pg-input", "#" + N).keypress(function (K) {
                                if ((K.charCode ? K.charCode : K.keyCode ? K.keyCode : 0) == 13) {
                                    d.p.page = a(this).val() > 0 ? a(this).val() : d.p.page;
                                    if (!H("user")) return false;
                                    ca();
                                    return false
                                }
                                return this
                            })
                        };
                        var oa = function (t, v, w, E) {
                            if (d.p.colModel[v].sortable) if (!(d.p.savedRow.length > 0)) {
                                if (!w) {
                                    if (d.p.lastsort == v) if (d.p.sortorder == "asc") d.p.sortorder = "desc";
                                    else {
                                        if (d.p.sortorder == "desc") d.p.sortorder = "asc"
                                    } else d.p.sortorder = d.p.colModel[v].firstsortorder || "asc";
                                    d.p.page = 1
                                }
                                if (E) if (d.p.lastsort == v && d.p.sortorder == E && !w) return;
                                else d.p.sortorder = E;
                                w = a("thead:first", d.grid.hDiv).get(0);
                                a("tr th:eq(" + d.p.lastsort + ") span.ui-grid-ico-sort", w).addClass("ui-state-disabled");
                                a("tr th:eq(" + d.p.lastsort + ")", w).attr("aria-selected", "false");
                                a("tr th:eq(" + v + ") span.ui-icon-" + d.p.sortorder, w).removeClass("ui-state-disabled");
                                a("tr th:eq(" + v + ")", w).attr("aria-selected", "true");
                                if (!d.p.viewsortcols[0]) if (d.p.lastsort != v) {
                                    a("tr th:eq(" + d.p.lastsort + ") span.s-ico", w).hide();
                                    a("tr th:eq(" + v + ") span.s-ico", w).show()
                                }
                                t = t.substring(5);
                                d.p.sortname = d.p.colModel[v].index || t;
                                w = d.p.sortorder;
                                if (a.isFunction(d.p.onSortCol)) if (d.p.onSortCol.call(d, t, v, w) == "stop") {
                                    d.p.lastsort = v;
                                    return
                                }
                                if (d.p.datatype == "local") d.p.deselectAfterSort && a(d).jqGrid("resetSelection");
                                else {
                                    d.p.selrow = null;
                                    d.p.multiselect && a("#cb_" + a.jgrid.jqID(d.p.id), d.grid.hDiv).attr("checked", false);
                                    d.p.selarrrow = [];
                                    d.p.savedRow = []
                                }
                                if (d.p.scroll) {
                                    w = d.grid.bDiv.scrollLeft;
                                    y(d.grid.bDiv, true);
                                    d.grid.hDiv.scrollLeft =
                                    w
                                }
                                d.p.subGrid && d.p.datatype == "local" && a("td.sgexpanded", "#" + a.jgrid.jqID(d.p.id)).each(function () {
                                    a(this).trigger("click")
                                });
                                ca();
                                d.p.lastsort = v;
                                if (d.p.sortname != t && v) d.p.lastsort = v
                            }
                        },
                            wa = function (t) {
                                var v = t,
                                    w;
                                for (w = t + 1; w < d.p.colModel.length; w++) if (d.p.colModel[w].hidden !== true) {
                                    v = w;
                                    break
                                }
                                return v - t
                            },
                            xa = function (t) {
                                var v, w = {},
                                    E = p ? 0 : d.p.cellLayout;
                                for (v = w[0] = w[1] = w[2] = 0; v <= t; v++) if (d.p.colModel[v].hidden === false) w[0] += d.p.colModel[v].width + E;
                                if (d.p.direction == "rtl") w[0] = d.p.width - w[0];
                                w[0] -= d.grid.bDiv.scrollLeft;
                                if (a(d.grid.cDiv).is(":visible")) w[1] += a(d.grid.cDiv).height() + parseInt(a(d.grid.cDiv).css("padding-top"), 10) + parseInt(a(d.grid.cDiv).css("padding-bottom"), 10);
                                if (d.p.toolbar[0] === true && (d.p.toolbar[1] == "top" || d.p.toolbar[1] == "both")) w[1] += a(d.grid.uDiv).height() + parseInt(a(d.grid.uDiv).css("border-top-width"), 10) + parseInt(a(d.grid.uDiv).css("border-bottom-width"), 10);
                                if (d.p.toppager) w[1] += a(d.grid.topDiv).height() + parseInt(a(d.grid.topDiv).css("border-bottom-width"), 10);
                                w[2] += a(d.grid.bDiv).height() + a(d.grid.hDiv).height();
                                return w
                            };
                        this.p.id = this.id;
                        if (a.inArray(d.p.multikey, ["shiftKey", "altKey", "ctrlKey"]) == -1) d.p.multikey = false;
                        d.p.keyIndex = false;
                        for (h = 0; h < d.p.colModel.length; h++) {
                            l = d.p.colModel[h];
                            l = a.extend(l, d.p.cmTemplate, l.template || {});
                            if (d.p.keyIndex === false && d.p.colModel[h].key === true) d.p.keyIndex = h
                        }
                        d.p.sortorder = d.p.sortorder.toLowerCase();
                        if (d.p.grouping === true) {
                            d.p.scroll = false;
                            d.p.rownumbers = false;
                            d.p.subGrid = false;
                            d.p.treeGrid = false;
                            d.p.gridview = true
                        }
                        if (this.p.treeGrid === true) {
                            try {
                                a(this).jqGrid("setTreeGrid")
                            } catch (Ba) {}
                            if (d.p.datatype != "local") d.p.localReader = {
                                id: "_id_"
                            }
                        }
                        if (this.p.subGrid) try {
                            a(d).jqGrid("setSubGrid")
                        } catch (Ga) {}
                        if (this.p.multiselect) {
                            this.p.colNames.unshift("<input role='checkbox' id='cb_" + this.p.id + "' class='cbox' type='checkbox'/>");
                            this.p.colModel.unshift({
                                name: "cb",
                                width: p ? d.p.multiselectWidth + d.p.cellLayout : d.p.multiselectWidth,
                                sortable: false,
                                resizable: false,
                                hidedlg: true,
                                search: false,
                                align: "center",
                                fixed: true
                            })
                        }
                        if (this.p.rownumbers) {
                            this.p.colNames.unshift("");
                            this.p.colModel.unshift({
                                name: "rn",
                                width: d.p.rownumWidth,
                                sortable: false,
                                resizable: false,
                                hidedlg: true,
                                search: false,
                                align: "center",
                                fixed: true
                            })
                        }
                        d.p.xmlReader = a.extend(true, {
                            root: "rows",
                            row: "row",
                            page: "rows>page",
                            total: "rows>total",
                            records: "rows>records",
                            repeatitems: true,
                            cell: "cell",
                            id: "[id]",
                            userdata: "userdata",
                            subgrid: {
                                root: "rows",
                                row: "row",
                                repeatitems: true,
                                cell: "cell"
                            }
                        }, d.p.xmlReader);
                        d.p.jsonReader = a.extend(true, {
                            root: "rows",
                            page: "page",
                            total: "total",
                            records: "records",
                            repeatitems: true,
                            cell: "cell",
                            id: "id",
                            userdata: "userdata",
                            subgrid: {
                                root: "rows",
                                repeatitems: true,
                                cell: "cell"
                            }
                        }, d.p.jsonReader);
                        d.p.localReader = a.extend(true, {
                            root: "rows",
                            page: "page",
                            total: "total",
                            records: "records",
                            repeatitems: false,
                            cell: "cell",
                            id: "id",
                            userdata: "userdata",
                            subgrid: {
                                root: "rows",
                                repeatitems: true,
                                cell: "cell"
                            }
                        }, d.p.localReader);
                        if (d.p.scroll) {
                            d.p.pgbuttons = false;
                            d.p.pginput = false;
                            d.p.rowList = []
                        }
                        d.p.data.length && A();
                        l = "<thead><tr class='ui-jqgrid-labels' role='rowheader'>";
                        var G, I, V, P, da, S, Q, ga;
                        I = ga = "";
                        if (d.p.shrinkToFit === true && d.p.forceFit === true) for (h = d.p.colModel.length - 1; h >= 0; h--) if (!d.p.colModel[h].hidden) {
                            d.p.colModel[h].resizable =
                            false;
                            break
                        }
                        if (d.p.viewsortcols[1] == "horizontal") {
                            ga = " ui-i-asc";
                            I = " ui-i-desc"
                        }
                        G = q ? "class='ui-th-div-ie'" : "";
                        ga = "<span class='s-ico' style='display:none'><span sort='asc' class='ui-grid-ico-sort ui-icon-asc" + ga + " ui-state-disabled ui-icon ui-icon-triangle-1-n ui-sort-" + j + "'></span>";
                        ga += "<span sort='desc' class='ui-grid-ico-sort ui-icon-desc" + I + " ui-state-disabled ui-icon ui-icon-triangle-1-s ui-sort-" + j + "'></span></span>";
                        for (h = 0; h < this.p.colNames.length; h++) {
                            I = d.p.headertitles ? ' title="' + a.jgrid.stripHtml(d.p.colNames[h]) + '"' : "";
                            l += "<th id='" + d.p.id + "_" + d.p.colModel[h].name + "' role='columnheader' class='ui-state-default ui-th-column ui-th-" + j + "'" + I + ">";
                            I = d.p.colModel[h].index || d.p.colModel[h].name;
                            l += "<div id='jqgh_" + d.p.colModel[h].name + "' " + G + ">" + d.p.colNames[h];
                            d.p.colModel[h].width = d.p.colModel[h].width ? parseInt(d.p.colModel[h].width, 10) : 150;
                            if (typeof d.p.colModel[h].title !== "boolean") d.p.colModel[h].title = true;
                            if (I == d.p.sortname) d.p.lastsort = h;
                            l += ga + "</div></th>"
                        }
                        l += "</tr></thead>";
                        ga = null;
                        a(this).append(l);
                        a("thead tr:first th", this).hover(function () {
                            a(this).addClass("ui-state-hover")
                        }, function () {
                            a(this).removeClass("ui-state-hover")
                        });
                        if (this.p.multiselect) {
                            var Z = [],
                                la;
                            a("#cb_" + a.jgrid.jqID(d.p.id), this).bind("click", function () {
                                d.p.selarrrow = [];
                                if (this.checked) {
                                    a("[id^=jqg_" + d.p.id + "_]").attr("checked", "checked");
                                    a(d.rows).each(function (t) {
                                        if (t > 0) if (!a(this).hasClass("subgrid") && !a(this).hasClass("jqgroup")) {
                                            a(this).addClass("ui-state-highlight").attr("aria-selected", "true");
                                            d.p.selarrrow.push(this.id);
                                            d.p.selrow = this.id
                                        }
                                    });
                                    la = true;
                                    Z = []
                                } else {
                                    a("[id^=jqg_" + d.p.id + "_]").removeAttr("checked");
                                    a(d.rows).each(function (t) {
                                        if (t > 0) if (!a(this).hasClass("subgrid")) {
                                            a(this).removeClass("ui-state-highlight").attr("aria-selected", "false");
                                            Z.push(this.id)
                                        }
                                    });
                                    d.p.selrow = null;
                                    la = false
                                }
                                if (a.isFunction(d.p.onSelectAll)) d.p.onSelectAll.call(d, la ? d.p.selarrrow : Z, la)
                            })
                        }
                        if (d.p.autowidth === true) {
                            l = a(u).innerWidth();
                            d.p.width = l > 0 ? l : "nw"
                        }(function () {
                            var t = 0,
                                v = d.p.cellLayout,
                                w = 0,
                                E, F = d.p.scrollOffset,
                                N, L = false,
                                T, $ = 0,
                                H = 0,
                                K = 0,
                                O;
                            if (p) v = 0;
                            a.each(d.p.colModel, function () {
                                if (typeof this.hidden === "undefined") this.hidden = false;
                                if (this.hidden === false) {
                                    t += s(this.width, 0);
                                    if (this.fixed) {
                                        $ += this.width;
                                        H += this.width + v
                                    } else w++;
                                    K++
                                }
                            });
                            if (isNaN(d.p.width)) d.p.width = g.width = t;
                            else g.width = d.p.width;
                            d.p.tblwidth = t;
                            if (d.p.shrinkToFit === false && d.p.forceFit === true) d.p.forceFit = false;
                            if (d.p.shrinkToFit === true && w > 0) {
                                T = g.width - v * w - H;
                                if (!isNaN(d.p.height)) {
                                    T -= F;
                                    L = true
                                }
                                t = 0;
                                a.each(d.p.colModel, function (X) {
                                    if (this.hidden === false && !this.fixed) {
                                        this.width = N = Math.round(T * this.width / (d.p.tblwidth - $));
                                        t += N;
                                        E = X
                                    }
                                });
                                O = 0;
                                if (L) {
                                    if (g.width - H - (t + v * w) !== F) O = g.width - H - (t + v * w) - F
                                } else if (!L && Math.abs(g.width - H - (t + v * w)) !== 1) O = g.width - H - (t + v * w);
                                d.p.colModel[E].width += O;
                                d.p.tblwidth = t + O + $ + K * v;
                                if (d.p.tblwidth > d.p.width) {
                                    d.p.colModel[E].width -= d.p.tblwidth - parseInt(d.p.width, 10);
                                    d.p.tblwidth = d.p.width
                                }
                            }
                        })();
                        a(u).css("width", g.width + "px").append("<div class='ui-jqgrid-resize-mark' id='rs_m" + d.p.id + "'>&#160;</div>");
                        a(k).css("width", g.width + "px");
                        l = a("thead:first", d).get(0);
                        var aa = "";
                        if (d.p.footerrow) aa += "<table role='grid' style='width:" + d.p.tblwidth + "px' class='ui-jqgrid-ftable' cellspacing='0' cellpadding='0' border='0'><tbody><tr role='row' class='ui-widget-content footrow footrow-" + j + "'>";
                        k = a("tr:first", l);
                        var ta = "<tr class='jqgfirstrow' role='row' style='height:auto'>";
                        d.p.disableClick = false;
                        a("th", k).each(function (t) {
                            V = d.p.colModel[t].width;
                            if (typeof d.p.colModel[t].resizable === "undefined") d.p.colModel[t].resizable = true;
                            if (d.p.colModel[t].resizable) {
                                P = document.createElement("span");
                                a(P).html("&#160;").addClass("ui-jqgrid-resize ui-jqgrid-resize-" + j);
                                a.browser.opera || a(P).css("cursor", "col-resize");
                                a(this).addClass(d.p.resizeclass)
                            } else P = "";
                            a(this).css("width", V + "px").prepend(P);
                            var v = "";
                            if (d.p.colModel[t].hidden) {
                                a(this).css("display", "none");
                                v = "display:none;"
                            }
                            ta += "<td role='gridcell' style='height:0px;width:" + V + "px;" + v + "'></td>";
                            g.headers[t] = {
                                width: V,
                                el: this
                            };
                            da = d.p.colModel[t].sortable;
                            if (typeof da !== "boolean") da = d.p.colModel[t].sortable = true;
                            v = d.p.colModel[t].name;
                            v == "cb" || v == "subgrid" || v == "rn" || d.p.viewsortcols[2] && a("div", this).addClass("ui-jqgrid-sortable");
                            if (da) if (d.p.viewsortcols[0]) {
                                a("div span.s-ico", this).show();
                                t == d.p.lastsort && a("div span.ui-icon-" + d.p.sortorder, this).removeClass("ui-state-disabled")
                            } else if (t == d.p.lastsort) {
                                a("div span.s-ico", this).show();
                                a("div span.ui-icon-" + d.p.sortorder, this).removeClass("ui-state-disabled")
                            }
                            if (d.p.footerrow) aa += "<td role='gridcell' " + n(t, 0, "") + ">&#160;</td>"
                        }).mousedown(function (t) {
                            if (a(t.target).closest("th>span.ui-jqgrid-resize").length == 1) {
                                var v = a.jgrid.getCellIndex(this);
                                if (d.p.forceFit === true) d.p.nv =
                                wa(v);
                                g.dragStart(v, t, xa(v));
                                return false
                            }
                        }).click(function (t) {
                            if (d.p.disableClick) return d.p.disableClick = false;
                            var v = "th>div.ui-jqgrid-sortable",
                                w, E;
                            d.p.viewsortcols[2] || (v = "th>div>span>span.ui-grid-ico-sort");
                            t = a(t.target).closest(v);
                            if (t.length == 1) {
                                v = a.jgrid.getCellIndex(this);
                                if (!d.p.viewsortcols[2]) {
                                    w = true;
                                    E = t.attr("sort")
                                }
                                oa(a("div", this)[0].id, v, w, E);
                                return false
                            }
                        });
                        if (d.p.sortable && a.fn.sortable) try {
                            a(d).jqGrid("sortableColumns", k)
                        } catch (ia) {}
                        if (d.p.footerrow) aa += "</tr></tbody></table>";
                        ta += "</tr>";
                        this.appendChild(document.createElement("tbody"));
                        a(this).addClass("ui-jqgrid-btable").append(ta);
                        ta = null;
                        k = a("<table class='ui-jqgrid-htable' style='width:" + d.p.tblwidth + "px' role='grid' aria-labelledby='gbox_" + this.id + "' cellspacing='0' cellpadding='0' border='0'></table>").append(l);
                        var qa = d.p.caption && d.p.hiddengrid === true ? true : false;
                        h = a("<div class='ui-jqgrid-hbox" + (j == "rtl" ? "-rtl" : "") + "'></div>");
                        l = null;
                        g.hDiv = document.createElement("div");
                        a(g.hDiv).css({
                            width: g.width + "px"
                        }).addClass("ui-state-default ui-jqgrid-hdiv").append(h);
                        a(h).append(k);
                        k = null;
                        qa && a(g.hDiv).hide();
                        if (d.p.pager) {
                            if (typeof d.p.pager == "string") {
                                if (d.p.pager.substr(0, 1) != "#") d.p.pager = "#" + d.p.pager
                            } else d.p.pager = "#" + a(d.p.pager).attr("id");
                            a(d.p.pager).css({
                                width: g.width + "px"
                            }).appendTo(u).addClass("ui-state-default ui-jqgrid-pager ui-corner-bottom");
                            qa && a(d.p.pager).hide();
                            m(d.p.pager, "")
                        }
                        d.p.cellEdit === false && d.p.hoverrows === true && a(d).bind("mouseover", function (t) {
                            Q = a(t.target).closest("tr.jqgrow");
                            a(Q).attr("class") !== "subgrid" && a(Q).addClass("ui-state-hover");
                            return false
                        }).bind("mouseout", function (t) {
                            Q = a(t.target).closest("tr.jqgrow");
                            a(Q).removeClass("ui-state-hover");
                            return false
                        });
                        var ma, pa;
                        a(d).before(g.hDiv).click(function (t) {
                            S = t.target;
                            var v = a(S).hasClass("cbox");
                            Q = a(S, d.rows).closest("tr.jqgrow");
                            if (a(Q).length === 0) return this;
                            var w = true;
                            if (a.isFunction(d.p.beforeSelectRow)) w = d.p.beforeSelectRow.call(d, Q[0].id, t);
                            if (S.tagName == "A" || (S.tagName == "INPUT" || S.tagName == "TEXTAREA" || S.tagName == "OPTION" || S.tagName == "SELECT") && !v) return this;
                            if (w === true) {
                                if (d.p.cellEdit === true) if (d.p.multiselect && v) a(d).jqGrid("setSelection", Q[0].id, true);
                                else {
                                    ma = Q[0].rowIndex;
                                    pa = a.jgrid.getCellIndex(S);
                                    try {
                                        a(d).jqGrid("editCell", ma, pa, true)
                                    } catch (E) {}
                                } else if (d.p.multikey) if (t[d.p.multikey]) a(d).jqGrid("setSelection", Q[0].id, true);
                                else {
                                    if (d.p.multiselect && v) {
                                        v = a("[id^=jqg_" + d.p.id + "_]").attr("checked");
                                        a("[id^=jqg_" + d.p.id + "_]").attr("checked", !v)
                                    }
                                } else {
                                    if (d.p.multiselect && d.p.multiboxonly) if (!v) {
                                        a(d.p.selarrrow).each(function (F, N) {
                                            F = d.rows.namedItem(N);
                                            a(F).removeClass("ui-state-highlight");
                                            a("#jqg_" + a.jgrid.jqID(d.p.id) + "_" + a.jgrid.jqID(N)).attr("checked", false)
                                        });
                                        d.p.selarrrow = [];
                                        a("#cb_" + a.jgrid.jqID(d.p.id), d.grid.hDiv).attr("checked", false)
                                    }
                                    a(d).jqGrid("setSelection", Q[0].id, true)
                                }
                                if (a.isFunction(d.p.onCellSelect)) {
                                    ma = Q[0].id;
                                    pa = a.jgrid.getCellIndex(S);
                                    d.p.onCellSelect.call(d, ma, pa, a(S).html(), t)
                                }
                                t.stopPropagation()
                            } else
                            return this
                        }).bind("reloadGrid", function (t, v) {
                            if (d.p.treeGrid === true) d.p.datatype = d.p.treedatatype;
                            v && v.current && d.grid.selectionPreserver(d);
                            if (d.p.datatype == "local") {
                                a(d).jqGrid("resetSelection");
                                d.p.data.length && A()
                            } else if (!d.p.treeGrid) {
                                d.p.selrow = null;
                                if (d.p.multiselect) {
                                    d.p.selarrrow = [];
                                    a("#cb_" + a.jgrid.jqID(d.p.id), d.grid.hDiv).attr("checked", false)
                                }
                                d.p.savedRow = []
                            }
                            d.p.scroll && y(d.grid.bDiv, true);
                            if (v && v.page) {
                                t = v.page;
                                if (t > d.p.lastpage) t = d.p.lastpage;
                                if (t < 1) t = 1;
                                d.p.page = t;
                                d.grid.bDiv.scrollTop = d.grid.prevRowHeight ? (t - 1) * d.grid.prevRowHeight * d.p.rowNum : 0
                            }
                            if (d.grid.prevRowHeight && d.p.scroll) {
                                delete d.p.lastpage;
                                d.grid.populateVisible()
                            } else d.grid.populate();
                            return false
                        });
                        a.isFunction(this.p.ondblClickRow) && a(this).dblclick(function (t) {
                            S = t.target;
                            Q = a(S, d.rows).closest("tr.jqgrow");
                            if (a(Q).length === 0) return false;
                            ma = Q[0].rowIndex;
                            pa = a.jgrid.getCellIndex(S);
                            d.p.ondblClickRow.call(d, a(Q).attr("id"), ma, pa, t);
                            return false
                        });
                        a.isFunction(this.p.onRightClickRow) && a(this).bind("contextmenu", function (t) {
                            S = t.target;
                            Q = a(S, d.rows).closest("tr.jqgrow");
                            if (a(Q).length === 0) return false;
                            d.p.multiselect || a(d).jqGrid("setSelection", Q[0].id, true);
                            ma = Q[0].rowIndex;
                            pa = a.jgrid.getCellIndex(S);
                            d.p.onRightClickRow.call(d, a(Q).attr("id"), ma, pa, t);
                            return false
                        });
                        g.bDiv = document.createElement("div");
                        a(g.bDiv).append(a('<div style="position:relative;' + (q && a.browser.version < 8 ? "height:0.01%;" : "") + '"></div>').append("<div></div>").append(this)).addClass("ui-jqgrid-bdiv").css({
                            height: d.p.height + (isNaN(d.p.height) ? "" : "px"),
                            width: g.width + "px"
                        }).scroll(g.scrollGrid);
                        a("table:first", g.bDiv).css({
                            width: d.p.tblwidth + "px"
                        });
                        if (q) {
                            a("tbody", this).size() == 2 && a("tbody:gt(0)", this).remove();
                            d.p.multikey && a(g.bDiv).bind("selectstart", function () {
                                return false
                            })
                        } else d.p.multikey && a(g.bDiv).bind("mousedown", function () {
                            return false
                        });
                        qa && a(g.bDiv).hide();
                        g.cDiv = document.createElement("div");
                        var Fa = d.p.hidegrid === true ? a("<a role='link' href='javascript:void(0)'/>").addClass("ui-jqgrid-titlebar-close HeaderButton").hover(function () {
                            Fa.addClass("ui-state-hover")
                        }, function () {
                            Fa.removeClass("ui-state-hover")
                        }).append("<span class='ui-icon ui-icon-circle-triangle-n'></span>").css(j == "rtl" ? "left" : "right", "0px") : "";
                        a(g.cDiv).append(Fa).append("<span class='ui-jqgrid-title" + (j == "rtl" ? "-rtl" : "") + "'>" + d.p.caption + "</span>").addClass("ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix");
                        a(g.cDiv).insertBefore(g.hDiv);
                        if (d.p.toolbar[0]) {
                            g.uDiv = document.createElement("div");
                            if (d.p.toolbar[1] == "top") a(g.uDiv).insertBefore(g.hDiv);
                            else d.p.toolbar[1] == "bottom" && a(g.uDiv).insertAfter(g.hDiv);
                            if (d.p.toolbar[1] == "both") {
                                g.ubDiv = document.createElement("div");
                                a(g.uDiv).insertBefore(g.hDiv).addClass("ui-userdata ui-state-default").attr("id", "t_" + this.id);
                                a(g.ubDiv).insertAfter(g.hDiv).addClass("ui-userdata ui-state-default").attr("id", "tb_" + this.id);
                                qa && a(g.ubDiv).hide()
                            } else a(g.uDiv).width(g.width).addClass("ui-userdata ui-state-default").attr("id", "t_" + this.id);
                            qa && a(g.uDiv).hide()
                        }
                        if (d.p.toppager) {
                            d.p.toppager = a.jgrid.jqID(d.p.id) + "_toppager";
                            g.topDiv = a("<div id='" + d.p.toppager + "'></div>")[0];
                            d.p.toppager = "#" + d.p.toppager;
                            a(g.topDiv).insertBefore(g.hDiv).addClass("ui-state-default ui-jqgrid-toppager").width(g.width);
                            m(d.p.toppager, "_t")
                        }
                        if (d.p.footerrow) {
                            g.sDiv = a("<div class='ui-jqgrid-sdiv'></div>")[0];
                            h = a("<div class='ui-jqgrid-hbox" + (j == "rtl" ? "-rtl" : "") + "'></div>");
                            a(g.sDiv).append(h).insertAfter(g.hDiv).width(g.width);
                            a(h).append(aa);
                            g.footers = a(".ui-jqgrid-ftable", g.sDiv)[0].rows[0].cells;
                            if (d.p.rownumbers) g.footers[0].className = "ui-state-default jqgrid-rownum";
                            qa && a(g.sDiv).hide()
                        }
                        h = null;
                        if (d.p.caption) {
                            var za = d.p.datatype;
                            if (d.p.hidegrid === true) {
                                a(".ui-jqgrid-titlebar-close", g.cDiv).click(function (t) {
                                    var v = a.isFunction(d.p.onHeaderClick),
                                        w = ".ui-jqgrid-bdiv, .ui-jqgrid-hdiv, .ui-jqgrid-pager, .ui-jqgrid-sdiv",
                                        E, F = this;
                                    if (d.p.toolbar[0] === true) {
                                        if (d.p.toolbar[1] == "both") w += ", #" + a(g.ubDiv).attr("id");
                                        w += ", #" + a(g.uDiv).attr("id")
                                    }
                                    E = a(w, "#gview_" + a.jgrid.jqID(d.p.id)).length;
                                    if (d.p.gridstate == "visible") a(w, "#gbox_" + a.jgrid.jqID(d.p.id)).slideUp("fast", function () {
                                        E--;
                                        if (E == 0) {
                                            a("span", F).removeClass("ui-icon-circle-triangle-n").addClass("ui-icon-circle-triangle-s");
                                            d.p.gridstate = "hidden";
                                            a("#gbox_" + a.jgrid.jqID(d.p.id)).hasClass("ui-resizable") && a(".ui-resizable-handle", "#gbox_" + a.jgrid.jqID(d.p.id)).hide();
                                            if (v) qa || d.p.onHeaderClick.call(d, d.p.gridstate, t)
                                        }
                                    });
                                    else d.p.gridstate == "hidden" && a(w, "#gbox_" + a.jgrid.jqID(d.p.id)).slideDown("fast", function () {
                                        E--;
                                        if (E == 0) {
                                            a("span", F).removeClass("ui-icon-circle-triangle-s").addClass("ui-icon-circle-triangle-n");
                                            if (qa) {
                                                d.p.datatype = za;
                                                ca();
                                                qa = false
                                            }
                                            d.p.gridstate = "visible";
                                            a("#gbox_" + a.jgrid.jqID(d.p.id)).hasClass("ui-resizable") && a(".ui-resizable-handle", "#gbox_" + a.jgrid.jqID(d.p.id)).show();
                                            if (v) qa || d.p.onHeaderClick.call(d, d.p.gridstate, t)
                                        }
                                    });
                                    return false
                                });
                                if (qa) {
                                    d.p.datatype = "local";
                                    a(".ui-jqgrid-titlebar-close", g.cDiv).trigger("click")
                                }
                            }
                        } else a(g.cDiv).hide();
                        a(g.hDiv).after(g.bDiv).mousemove(function (t) {
                            if (g.resizing) {
                                g.dragMove(t);
                                return false
                            }
                        });
                        a(".ui-jqgrid-labels", g.hDiv).bind("selectstart", function () {
                            return false
                        });
                        a(document).mouseup(function () {
                            if (g.resizing) {
                                g.dragEnd();
                                return false
                            }
                            return true
                        });
                        d.formatCol = n;
                        d.sortData = oa;
                        d.updatepager = function (t, v) {
                            var w, E, F, N, L, T, $, H = "";
                            F = parseInt(d.p.page, 10) - 1;
                            if (F < 0) F = 0;
                            F *= parseInt(d.p.rowNum, 10);
                            L = F + d.p.reccount;
                            if (d.p.scroll) {
                                w = a("tbody:first > tr:gt(0)", d.grid.bDiv);
                                F = L - w.length;
                                d.p.reccount = w.length;
                                if (E = w.outerHeight() || d.grid.prevRowHeight) {
                                    w = F * E;
                                    E = parseInt(d.p.records, 10) * E;
                                    a(">div:first", d.grid.bDiv).css({
                                        height: E
                                    }).children("div:first").css({
                                        height: w,
                                        display: w ? "" : "none"
                                    })
                                }
                                d.grid.bDiv.scrollLeft = d.grid.hDiv.scrollLeft
                            }
                            H = d.p.pager ? d.p.pager : "";
                            H += d.p.toppager ? H ? "," + d.p.toppager : d.p.toppager : "";
                            if (H) {
                                $ = a.jgrid.formatter.integer || {};
                                w = s(d.p.page);
                                E = s(d.p.lastpage);
                                a(".selbox", H).attr("disabled", false);
                                if (d.p.pginput === true) {
                                    a(".ui-pg-input", H).val(d.p.page);
                                    a("#sp_1", H).html(a.fmatter ? a.fmatter.util.NumberFormat(d.p.lastpage, $) : d.p.lastpage)
                                }
                                if (d.p.viewrecords) if (d.p.reccount === 0) a(".ui-paging-info", H).html(d.p.emptyrecords);
                                else {
                                    N = F + 1;
                                    T = d.p.records;
                                    if (a.fmatter) {
                                        N = a.fmatter.util.NumberFormat(N, $);
                                        L = a.fmatter.util.NumberFormat(L, $);
                                        T = a.fmatter.util.NumberFormat(T, $)
                                    }
                                    a(".ui-paging-info", H).html(a.jgrid.format(d.p.recordtext, N, L, T))
                                }
                                if (d.p.pgbuttons === true) {
                                    if (w <= 0) w = E = 0;
                                    if (w == 1 || w === 0) {
                                        a("#first, #prev", d.p.pager).addClass("ui-state-disabled").removeClass("ui-state-hover");
                                        d.p.toppager && a("#first_t, #prev_t", d.p.toppager).addClass("ui-state-disabled").removeClass("ui-state-hover")
                                    } else {
                                        a("#first, #prev", d.p.pager).removeClass("ui-state-disabled");
                                        d.p.toppager && a("#first_t, #prev_t", d.p.toppager).removeClass("ui-state-disabled")
                                    }
                                    if (w == E || w === 0) {
                                        a("#next, #last", d.p.pager).addClass("ui-state-disabled").removeClass("ui-state-hover");
                                        d.p.toppager && a("#next_t, #last_t", d.p.toppager).addClass("ui-state-disabled").removeClass("ui-state-hover")
                                    } else {
                                        a("#next, #last", d.p.pager).removeClass("ui-state-disabled");
                                        d.p.toppager && a("#next_t, #last_t", d.p.toppager).removeClass("ui-state-disabled")
                                    }
                                }
                            }
                            t === true && d.p.rownumbers === true && a("td.jqgrid-rownum", d.rows).each(function (K) {
                                a(this).html(F + 1 + K)
                            });
                            v && d.p.jqgdnd && a(d).jqGrid("gridDnD", "updateDnD");
                            a.isFunction(d.p.gridComplete) && d.p.gridComplete.call(d)
                        };
                        d.refreshIndex = A;
                        d.formatter = function (t, v, w, E, F) {
                            return r(t, v, w, E, F)
                        };
                        a.extend(g, {
                            populate: ca,
                            emptyRows: y
                        });
                        this.grid = g;
                        d.addXmlData = function (t) {
                            J(t, d.grid.bDiv)
                        };
                        d.addJSONData = function (t) {
                            M(t, d.grid.bDiv)
                        };
                        this.grid.cols = this.rows[0].cells;
                        ca();
                        d.p.hiddengrid = false;
                        a(window).unload(function () {
                            d = null
                        })
                    }
                }
            }
        })
    };
    a.jgrid.extend({
        getGridParam: function (c) {
            var e = this[0];
            if (e && e.grid) return c ? typeof e.p[c] != "undefined" ? e.p[c] : null : e.p
        },
        setGridParam: function (c) {
            return this.each(function () {
                this.grid && typeof c === "object" && a.extend(true, this.p, c)
            })
        },
        getDataIDs: function () {
            var c = [],
                e = 0,
                b, f = 0;
            this.each(function () {
                if ((b = this.rows.length) && b > 0) for (; e < b;) {
                    if (a(this.rows[e]).hasClass("jqgrow")) {
                        c[f] = this.rows[e].id;
                        f++
                    }
                    e++
                }
            });
            return c
        },
        setSelection: function (c, e) {
            return this.each(function () {
                function b(d) {
                    var l = a(f.grid.bDiv)[0].clientHeight,
                        k = a(f.grid.bDiv)[0].scrollTop,
                        m = f.rows[d].offsetTop;
                    d = f.rows[d].clientHeight;
                    if (m + d >= l + k) a(f.grid.bDiv)[0].scrollTop = m - (l + k) + d + k;
                    else if (m < l + k) if (m < k) a(f.grid.bDiv)[0].scrollTop = m
                }
                var f = this,
                    g, h, j;
                if (c !== undefined) {
                    e = e === false ? false : true;
                    if (h =
                    f.rows.namedItem(c + "")) {
                        if (f.p.scrollrows === true) {
                            g = f.rows.namedItem(c).rowIndex;
                            g >= 0 && b(g)
                        }
                        if (f.p.multiselect) {
                            f.p.selrow = h.id;
                            j = a.inArray(f.p.selrow, f.p.selarrrow);
                            if (j === -1) {
                                h.className !== "ui-subgrid" && a(h).addClass("ui-state-highlight").attr("aria-selected", "true");
                                g = true;
                                a("#jqg_" + a.jgrid.jqID(f.p.id) + "_" + a.jgrid.jqID(f.p.selrow)).attr("checked", g);
                                f.p.selarrrow.push(f.p.selrow)
                            } else {
                                h.className !== "ui-subgrid" && a(h).removeClass("ui-state-highlight").attr("aria-selected", "false");
                                g = false;
                                a("#jqg_" + a.jgrid.jqID(f.p.id) + "_" + a.jgrid.jqID(f.p.selrow)).attr("checked", g);
                                f.p.selarrrow.splice(j, 1);
                                j = f.p.selarrrow[0];
                                f.p.selrow = j === undefined ? null : j
                            }
                            f.p.onSelectRow && e && f.p.onSelectRow.call(f, h.id, g)
                        } else if (h.className !== "ui-subgrid") {
                            f.p.selrow && a(f.rows.namedItem(f.p.selrow)).removeClass("ui-state-highlight").attr("aria-selected", "false");
                            if (f.p.selrow != h.id) {
                                f.p.selrow = h.id;
                                a(h).addClass("ui-state-highlight").attr("aria-selected", "true");
                                g = true
                            } else {
                                g = false;
                                f.p.selrow = null
                            }
                            f.p.onSelectRow && e && f.p.onSelectRow.call(f, h.id, g)
                        }
                    }
                }
            })
        },
        resetSelection: function () {
            return this.each(function () {
                var c = this,
                    e;
                if (c.p.multiselect) {
                    a(c.p.selarrrow).each(function (b, f) {
                        e = c.rows.namedItem(f);
                        a(e).removeClass("ui-state-highlight").attr("aria-selected", "false");
                        a("#jqg_" + a.jgrid.jqID(c.p.id) + "_" + a.jgrid.jqID(f)).attr("checked", false)
                    });
                    a("#cb_" + a.jgrid.jqID(c.p.id)).attr("checked", false);
                    c.p.selarrrow = []
                } else if (c.p.selrow) {
                    a("#" + a.jgrid.jqID(c.p.id) + " tbody:first tr#" + a.jgrid.jqID(c.p.selrow)).removeClass("ui-state-highlight").attr("aria-selected", "false");
                    c.p.selrow = null
                }
                c.p.savedRow = []
            })
        },
        getRowData: function (c) {
            var e = {},
                b, f = false,
                g, h = 0;
            this.each(function () {
                var j = this,
                    d, l;
                if (typeof c == "undefined") {
                    f = true;
                    b = [];
                    g = j.rows.length
                } else {
                    l = j.rows.namedItem(c);
                    if (!l) return e;
                    g = 2
                }
                for (; h < g;) {
                    if (f) l = j.rows[h];
                    if (a(l).hasClass("jqgrow")) {
                        a("td", l).each(function (k) {
                            d = j.p.colModel[k].name;
                            if (d !== "cb" && d !== "subgrid" && d !== "rn") if (j.p.treeGrid === true && d == j.p.ExpandColumn) e[d] = a.jgrid.htmlDecode(a("span:first", this).html());
                            else
                            try {
                                e[d] = a.unformat(this, {
                                    rowId: l.id,
                                    colModel: j.p.colModel[k]
                                }, k)
                            } catch (m) {
                                e[d] = a.jgrid.htmlDecode(a(this).html())
                            }
                        });
                        if (f) {
                            b.push(e);
                            e = {}
                        }
                    }
                    h++
                }
            });
            return b ? b : e
        },
        delRowData: function (c) {
            var e = false,
                b, f;
            this.each(function () {
                var g = this;
                if (b = g.rows.namedItem(c)) {
                    a(b).remove();
                    g.p.records--;
                    g.p.reccount--;
                    g.updatepager(true, false);
                    e = true;
                    if (g.p.multiselect) {
                        f = a.inArray(c, g.p.selarrrow);
                        f != -1 && g.p.selarrrow.splice(f, 1)
                    }
                    if (c == g.p.selrow) g.p.selrow = null
                } else
                return false;
                if (g.p.datatype == "local") {
                    var h = g.p._index[c];
                    if (typeof h != "undefined") {
                        g.p.data.splice(h, 1);
                        g.refreshIndex()
                    }
                }
                if (g.p.altRows === true && e) {
                    var j = g.p.altclass;
                    a(g.rows).each(function (d) {
                        d % 2 == 1 ? a(this).addClass(j) : a(this).removeClass(j)
                    })
                }
            });
            return e
        },
        setRowData: function (c, e, b) {
            var f, g = true,
                h;
            this.each(function () {
                if (!this.grid) return false;
                var j = this,
                    d, l, k = typeof b,
                    m = {};
                l = j.rows.namedItem(c);
                if (!l) return false;
                if (e) try {
                    a(this.p.colModel).each(function (u) {
                        f = this.name;
                        if (e[f] !== undefined) {
                            m[f] = this.formatter && typeof this.formatter === "string" && this.formatter == "date" ? a.unformat.date(e[f], this) : e[f];
                            d = j.formatter(c, e[f], u, e, "edit");
                            h = this.title ? {
                                title: a.jgrid.stripHtml(d)
                            } : {};
                            j.p.treeGrid === true && f == j.p.ExpandColumn ? a("td:eq(" + u + ") > span:first", l).html(d).attr(h) : a("td:eq(" + u + ")", l).html(d).attr(h)
                        }
                    });
                    if (j.p.datatype == "local") {
                        var q = j.p._index[c];
                        if (typeof q != "undefined") j.p.data[q] = a.extend(true, j.p.data[q], m);
                        m = null
                    }
                } catch (p) {
                    g = false
                }
                if (g) if (k === "string") a(l).addClass(b);
                else k === "object" && a(l).css(b)
            });
            return g
        },
        addRowData: function (c, e, b, f) {
            b || (b = "last");
            var g = false,
                h, j, d, l, k, m, q, p, u = "",
                s, n, o, r, C;
            if (e) {
                if (a.isArray(e)) {
                    s = true;
                    b = "last";
                    n = c
                } else {
                    e = [e];
                    s = false
                }
                this.each(function () {
                    var x = this,
                        B = e.length;
                    k = x.p.rownumbers === true ? 1 : 0;
                    d = x.p.multiselect === true ? 1 : 0;
                    l = x.p.subGrid === true ? 1 : 0;
                    if (!s) if (typeof c != "undefined") c += "";
                    else {
                        c = x.p.records + 1 + "";
                        if (x.p.keyIndex !== false) {
                            n = x.p.colModel[x.p.keyIndex + d + l + k].name;
                            if (typeof e[0][n] != "undefined") c = e[0][n]
                        }
                    }
                    o = x.p.altclass;
                    for (var D = 0, z = "", y = {}, A = a.isFunction(x.p.afterInsertRow) ? true : false; D < B;) {
                        r = e[D];
                        j = "";
                        if (s) {
                            try {
                                c = r[n]
                            } catch (J) {
                                c = x.p.records + 1 + ""
                            }
                            z = x.p.altRows === true ? (x.rows.length - 1) % 2 === 0 ? o : "" : ""
                        }
                        if (k) {
                            u = x.formatCol(0, 1, "");
                            j += '<td role="gridcell" aria-describedby="' + x.p.id + '_rn" class="ui-state-default jqgrid-rownum" ' + u + ">0</td>"
                        }
                        if (d) {
                            p = '<input role="checkbox" type="checkbox" id="jqg_' + x.p.id + "_" + c + '" class="cbox"/>';
                            u = x.formatCol(k, 1, "");
                            j += '<td role="gridcell" aria-describedby="' + x.p.id + '_cb" ' + u + ">" + p + "</td>"
                        }
                        if (l) j += a(x).jqGrid("addSubGridCell", d + k, 1);
                        for (q = d + l + k; q < x.p.colModel.length; q++) {
                            C = x.p.colModel[q];
                            h = C.name;
                            y[h] = C.formatter && typeof C.formatter === "string" && C.formatter == "date" ? a.unformat.date(r[h], C) : r[h];
                            p = x.formatter(c, a.jgrid.getAccessor(r, h), q, r, "edit");
                            u = x.formatCol(q, 1, p);
                            j += '<td role="gridcell" aria-describedby="' + x.p.id + "_" + h + '" ' + u + ">" + p + "</td>"
                        }
                        j = '<tr id="' + c + '" role="row" class="ui-widget-content jqgrow ui-row-' + x.p.direction + " " + z + '">' + j + "</tr>";
                        if (x.p.subGrid === true) {
                            j = a(j)[0];
                            a(x).jqGrid("addSubGrid", j, d + k)
                        }
                        if (x.rows.length === 0) a("table:first", x.grid.bDiv).append(j);
                        else
                        switch (b) {
                        case "last":
                            a(x.rows[x.rows.length - 1]).after(j);
                            break;
                        case "first":
                            a(x.rows[0]).after(j);
                            break;
                        case "after":
                            if (m = x.rows.namedItem(f)) a(x.rows[m.rowIndex + 1]).hasClass("ui-subgrid") ? a(x.rows[m.rowIndex + 1]).after(j) : a(m).after(j);
                            break;
                        case "before":
                            if (m = x.rows.namedItem(f)) {
                                a(m).before(j);
                                m = m.rowIndex
                            }
                            break
                        }
                        x.p.records++;
                        x.p.reccount++;
                        A && x.p.afterInsertRow.call(x, c, r, r);
                        D++;
                        if (x.p.datatype == "local") {
                            x.p._index[c] = x.p.data.length;
                            x.p.data.push(y);
                            y = {}
                        }
                    }
                    if (x.p.altRows === true && !s) if (b == "last")(x.rows.length - 1) % 2 == 1 && a(x.rows[x.rows.length - 1]).addClass(o);
                    else a(x.rows).each(function (M) {
                        M % 2 == 1 ? a(this).addClass(o) : a(this).removeClass(o)
                    });
                    x.updatepager(true, true);
                    g = true
                })
            }
            return g
        },
        footerData: function (c, e, b) {
            function f(l) {
                for (var k in l) if (l.hasOwnProperty(k)) return false;
                return true
            }
            var g, h = false,
                j = {},
                d;
            if (typeof c == "undefined") c = "get";
            if (typeof b != "boolean") b = true;
            c = c.toLowerCase();
            this.each(function () {
                var l = this,
                    k;
                if (!l.grid || !l.p.footerrow) return false;
                if (c == "set") if (f(e)) return false;
                h = true;
                a(this.p.colModel).each(function (m) {
                    g =
                    this.name;
                    if (c == "set") {
                        if (e[g] !== undefined) {
                            k = b ? l.formatter("", e[g], m, e, "edit") : e[g];
                            d = this.title ? {
                                title: a.jgrid.stripHtml(k)
                            } : {};
                            a("tr.footrow td:eq(" + m + ")", l.grid.sDiv).html(k).attr(d);
                            h = true
                        }
                    } else if (c == "get") j[g] = a("tr.footrow td:eq(" + m + ")", l.grid.sDiv).html()
                })
            });
            return c == "get" ? j : h
        },
        ShowHideCol: function (c, e) {
            return this.each(function () {
                var b = this,
                    f = false;
                if (b.grid) {
                    if (typeof c === "string") c = [c];
                    e = e != "none" ? "" : "none";
                    var g = e === "" ? true : false;
                    a(this.p.colModel).each(function (h) {
                        if (a.inArray(this.name, c) !== -1 && this.hidden === g) {
                            a("tr", b.grid.hDiv).each(function () {
                                a(this).children("th:eq(" + h + ")").css("display", e)
                            });
                            a(b.rows).each(function () {
                                a(this).children("td:eq(" + h + ")").css("display", e)
                            });
                            b.p.footerrow && a(b.grid.sDiv).children("td:eq(" + h + ")").css("display", e);
                            if (e == "none") b.p.tblwidth -= this.width + b.p.cellLayout;
                            else b.p.tblwidth += this.width;
                            this.hidden = !g;
                            f = true
                        }
                    });
                    if (f === true) {
                        a("table:first", b.grid.hDiv).width(b.p.tblwidth);
                        a("table:first", b.grid.bDiv).width(b.p.tblwidth);
                        b.grid.hDiv.scrollLeft =
                        b.grid.bDiv.scrollLeft;
                        if (b.p.footerrow) {
                            a("table:first", b.grid.sDiv).width(b.p.tblwidth);
                            b.grid.sDiv.scrollLeft = b.grid.bDiv.scrollLeft
                        }
                        b.p.shrinkToFit === true && a(b).jqGrid("setGridWidth", b.grid.width - 0.001, true)
                    }
                }
            })
        },
        hideCol: function (c) {
            return this.each(function () {
                a(this).jqGrid("ShowHideCol", c, "none")
            })
        },
        showCol: function (c) {
            return this.each(function () {
                a(this).jqGrid("ShowHideCol", c, "")
            })
        },
        remapColumns: function (c, e, b) {
            function f(j) {
                var d;
                d = j.length ? a.makeArray(j) : a.extend({}, j);
                a.each(c, function (l) {
                    j[l] =
                    d[this]
                })
            }
            function g(j, d) {
                a(">tr" + (d || ""), j).each(function () {
                    var l = this,
                        k = a.makeArray(l.cells);
                    a.each(c, function () {
                        var m = k[this];
                        m && l.appendChild(m)
                    })
                })
            }
            var h = this.get(0);
            f(h.p.colModel);
            f(h.p.colNames);
            f(h.grid.headers);
            g(a("thead:first", h.grid.hDiv), b && ":not(.ui-jqgrid-labels)");
            e && g(a("#" + a.jgrid.jqID(h.p.id) + " tbody:first"), ".jqgfirstrow, tr.jqgrow, tr.jqfoot");
            h.p.footerrow && g(a("tbody:first", h.grid.sDiv));
            if (h.p.remapColumns) if (h.p.remapColumns.length) f(h.p.remapColumns);
            else h.p.remapColumns =
            a.makeArray(c);
            h.p.lastsort = a.inArray(h.p.lastsort, c);
            if (h.p.treeGrid) h.p.expColInd = a.inArray(h.p.expColInd, c)
        },
        setGridWidth: function (c, e) {
            return this.each(function () {
                if (this.grid) {
                    var b = this,
                        f, g = 0,
                        h = b.p.cellLayout,
                        j, d = 0,
                        l = false,
                        k = b.p.scrollOffset,
                        m, q = 0,
                        p = 0,
                        u = 0,
                        s;
                    if (typeof e != "boolean") e = b.p.shrinkToFit;
                    if (!isNaN(c)) {
                        c = parseInt(c, 10);
                        b.grid.width = b.p.width = c;
                        a("#gbox_" + a.jgrid.jqID(b.p.id)).css("width", c + "px");
                        a("#gview_" + a.jgrid.jqID(b.p.id)).css("width", c + "px");
                        a(b.grid.bDiv).css("width", c + "px");
                        a(b.grid.hDiv).css("width", c + "px");
                        b.p.pager && a(b.p.pager).css("width", c + "px");
                        b.p.toppager && a(b.p.toppager).css("width", c + "px");
                        if (b.p.toolbar[0] === true) {
                            a(b.grid.uDiv).css("width", c + "px");
                            b.p.toolbar[1] == "both" && a(b.grid.ubDiv).css("width", c + "px")
                        }
                        b.p.footerrow && a(b.grid.sDiv).css("width", c + "px");
                        if (e === false && b.p.forceFit === true) b.p.forceFit = false;
                        if (e === true) {
                            if (a.browser.safari) h = 0;
                            a.each(b.p.colModel, function () {
                                if (this.hidden === false) {
                                    g += parseInt(this.width, 10);
                                    if (this.fixed) {
                                        p += this.width;
                                        q += this.width + h
                                    } else d++;
                                    u++
                                }
                            });
                            if (d !== 0) {
                                b.p.tblwidth = g;
                                m = c - h * d - q;
                                if (!isNaN(b.p.height)) if (a(b.grid.bDiv)[0].clientHeight < a(b.grid.bDiv)[0].scrollHeight || b.rows.length === 1) {
                                    l = true;
                                    m -= k
                                }
                                g = 0;
                                var n = b.grid.cols.length > 0;
                                a.each(b.p.colModel, function (o) {
                                    if (this.hidden === false && !this.fixed) {
                                        f = Math.round(m * this.width / (b.p.tblwidth - p));
                                        if (!(f < 0)) {
                                            this.width = f;
                                            g += f;
                                            b.grid.headers[o].width = f;
                                            b.grid.headers[o].el.style.width = f + "px";
                                            if (b.p.footerrow) b.grid.footers[o].style.width = f + "px";
                                            if (n) b.grid.cols[o].style.width =
                                            f + "px";
                                            j = o
                                        }
                                    }
                                });
                                s = 0;
                                if (l) {
                                    if (c - q - (g + h * d) !== k) s = c - q - (g + h * d) - k
                                } else if (Math.abs(c - q - (g + h * d)) !== 1) s = c - q - (g + h * d);
                                b.p.colModel[j].width += s;
                                b.p.tblwidth = g + s + p + h * u;
                                if (b.p.tblwidth > c) {
                                    l = b.p.tblwidth - parseInt(c, 10);
                                    b.p.tblwidth = c;
                                    f = b.p.colModel[j].width -= l
                                } else f = b.p.colModel[j].width;
                                b.grid.headers[j].width = f;
                                b.grid.headers[j].el.style.width = f + "px";
                                if (n) b.grid.cols[j].style.width = f + "px";
                                a("table:first", b.grid.bDiv).css("width", b.p.tblwidth + "px");
                                a("table:first", b.grid.hDiv).css("width", b.p.tblwidth + "px");
                                b.grid.hDiv.scrollLeft = b.grid.bDiv.scrollLeft;
                                if (b.p.footerrow) {
                                    b.grid.footers[j].style.width = f + "px";
                                    a("table:first", b.grid.sDiv).css("width", b.p.tblwidth + "px")
                                }
                            }
                        }
                    }
                }
            })
        },
        setGridHeight: function (c) {
            return this.each(function () {
                var e = this;
                if (e.grid) {
                    a(e.grid.bDiv).css({
                        height: c + (isNaN(c) ? "" : "px")
                    });
                    e.p.height = c;
                    e.p.scroll && e.grid.populateVisible()
                }
            })
        },
        setCaption: function (c) {
            return this.each(function () {
                this.p.caption = c;
                a("span.ui-jqgrid-title", this.grid.cDiv).html(c);
                a(this.grid.cDiv).show()
            })
        },
        setLabel: function (c, e, b, f) {
            return this.each(function () {
                var g = this,
                    h = -1;
                if (g.grid) {
                    if (isNaN(c)) a(g.p.colModel).each(function (l) {
                        if (this.name == c) {
                            h = l;
                            return false
                        }
                    });
                    else h = parseInt(c, 10);
                    if (h >= 0) {
                        var j = a("tr.ui-jqgrid-labels th:eq(" + h + ")", g.grid.hDiv);
                        if (e) {
                            var d = a(".s-ico", j);
                            a("[id^=jqgh_]", j).empty().html(e).append(d);
                            g.p.colNames[h] = e
                        }
                        if (b) typeof b === "string" ? a(j).addClass(b) : a(j).css(b);
                        typeof f === "object" && a(j).attr(f)
                    }
                }
            })
        },
        setCell: function (c, e, b, f, g, h) {
            return this.each(function () {
                var j = this,
                    d = -1,
                    l, k;
                if (j.grid) {
                    if (isNaN(e)) a(j.p.colModel).each(function (q) {
                        if (this.name == e) {
                            d = q;
                            return false
                        }
                    });
                    else d = parseInt(e, 10);
                    if (d >= 0) if (l = j.rows.namedItem(c)) {
                        var m = a("td:eq(" + d + ")", l);
                        if (b !== "" || h === true) {
                            l = j.formatter(c, b, d, l, "edit");
                            k = j.p.colModel[d].title ? {
                                title: a.jgrid.stripHtml(l)
                            } : {};
                            j.p.treeGrid && a(".tree-wrap", a(m)).length > 0 ? a("span", a(m)).html(l).attr(k) : a(m).html(l).attr(k);
                            if (j.p.datatype == "local") {
                                l = j.p.colModel[d];
                                b = l.formatter && typeof l.formatter === "string" && l.formatter == "date" ? a.unformat.date(b, l) : b;
                                k = j.p._index[c];
                                if (typeof k != "undefined") j.p.data[k][l.name] =
                                b
                            }
                        }
                        if (typeof f === "string") a(m).addClass(f);
                        else f && a(m).css(f);
                        typeof g === "object" && a(m).attr(g)
                    }
                }
            })
        },
        getCell: function (c, e) {
            var b = false;
            this.each(function () {
                var f = this,
                    g = -1;
                if (f.grid) {
                    if (isNaN(e)) a(f.p.colModel).each(function (d) {
                        if (this.name === e) {
                            g = d;
                            return false
                        }
                    });
                    else g = parseInt(e, 10);
                    if (g >= 0) {
                        var h = f.rows.namedItem(c);
                        if (h) try {
                            b = a.unformat(a("td:eq(" + g + ")", h), {
                                rowId: h.id,
                                colModel: f.p.colModel[g]
                            }, g)
                        } catch (j) {
                            b = a.jgrid.htmlDecode(a("td:eq(" + g + ")", h).html())
                        }
                    }
                }
            });
            return b
        },
        getCol: function (c, e, b) {
            var f = [],
                g, h = 0;
            e = typeof e != "boolean" ? false : e;
            if (typeof b == "undefined") b = false;
            this.each(function () {
                var j = this,
                    d = -1;
                if (j.grid) {
                    if (isNaN(c)) a(j.p.colModel).each(function (q) {
                        if (this.name === c) {
                            d = q;
                            return false
                        }
                    });
                    else d = parseInt(c, 10);
                    if (d >= 0) {
                        var l = j.rows.length,
                            k = 0;
                        if (l && l > 0) {
                            for (; k < l;) {
                                if (a(j.rows[k]).hasClass("jqgrow")) {
                                    try {
                                        g = a.unformat(a(j.rows[k].cells[d]), {
                                            rowId: j.rows[k].id,
                                            colModel: j.p.colModel[d]
                                        }, d)
                                    } catch (m) {
                                        g = a.jgrid.htmlDecode(j.rows[k].cells[d].innerHTML)
                                    }
                                    if (b) h += parseFloat(g);
                                    else if (e) f.push({
                                        id: j.rows[k].id,
                                        value: g
                                    });
                                    else f[k] = g
                                }
                                k++
                            }
                            if (b) switch (b.toLowerCase()) {
                            case "sum":
                                f = h;
                                break;
                            case "avg":
                                f = h / l;
                                break;
                            case "count":
                                f = l;
                                break
                            }
                        }
                    }
                }
            });
            return f
        },
        clearGridData: function (c) {
            return this.each(function () {
                var e = this;
                if (e.grid) {
                    if (typeof c != "boolean") c = false;
                    if (e.p.deepempty) a("#" + a.jgrid.jqID(e.p.id) + " tbody:first tr:gt(0)").remove();
                    else {
                        var b = a("#" + a.jgrid.jqID(e.p.id) + " tbody:first tr:first")[0];
                        a("#" + a.jgrid.jqID(e.p.id) + " tbody:first").empty().append(b)
                    }
                    e.p.footerrow && c && a(".ui-jqgrid-ftable td", e.grid.sDiv).html("&#160;");
                    e.p.selrow = null;
                    e.p.selarrrow = [];
                    e.p.savedRow = [];
                    e.p.records = 0;
                    e.p.page = 1;
                    e.p.lastpage = 0;
                    e.p.reccount = 0;
                    e.p.data = [];
                    e.p_index = {};
                    e.updatepager(true, false)
                }
            })
        },
        getInd: function (c, e) {
            var b = false,
                f;
            this.each(function () {
                if (f = this.rows.namedItem(c)) b = e === true ? f : f.rowIndex
            });
            return b
        }
    })
})(jQuery);
(function (a) {
    a.jgrid.extend({
        editCell: function (c, e, b) {
            return this.each(function () {
                var f = this,
                    g, h, j;
                if (!(!f.grid || f.p.cellEdit !== true)) {
                    e = parseInt(e, 10);
                    f.p.selrow = f.rows[c].id;
                    f.p.knv || a(f).jqGrid("GridNav");
                    if (f.p.savedRow.length > 0) {
                        if (b === true) if (c == f.p.iRow && e == f.p.iCol) return;
                        a(f).jqGrid("saveCell", f.p.savedRow[0].id, f.p.savedRow[0].ic)
                    } else window.setTimeout(function () {
                        a("#" + f.p.knv).attr("tabindex", "-1").focus()
                    }, 0);
                    g = f.p.colModel[e].name;
                    if (!(g == "subgrid" || g == "cb" || g == "rn")) {
                        j = a("td:eq(" + e + ")", f.rows[c]);
                        if (f.p.colModel[e].editable === true && b === true && !j.hasClass("not-editable-cell")) {
                            if (parseInt(f.p.iCol, 10) >= 0 && parseInt(f.p.iRow, 10) >= 0) {
                                a("td:eq(" + f.p.iCol + ")", f.rows[f.p.iRow]).removeClass("edit-cell ui-state-highlight");
                                a(f.rows[f.p.iRow]).removeClass("selected-row ui-state-hover")
                            }
                            a(j).addClass("edit-cell ui-state-highlight");
                            a(f.rows[c]).addClass("selected-row ui-state-hover");
                            try {
                                h = a.unformat(j, {
                                    rowId: f.rows[c].id,
                                    colModel: f.p.colModel[e]
                                }, e)
                            } catch (d) {
                                h = a(j).html()
                            }
                            if (f.p.autoencode) h =
                            a.jgrid.htmlDecode(h);
                            if (!f.p.colModel[e].edittype) f.p.colModel[e].edittype = "text";
                            f.p.savedRow.push({
                                id: c,
                                ic: e,
                                name: g,
                                v: h
                            });
                            if (a.isFunction(f.p.formatCell)) {
                                var l = f.p.formatCell.call(f, f.rows[c].id, g, h, c, e);
                                if (l !== undefined) h = l
                            }
                            l = a.extend({}, f.p.colModel[e].editoptions || {}, {
                                id: c + "_" + g,
                                name: g
                            });
                            var k = a.jgrid.createEl(f.p.colModel[e].edittype, l, h, true, a.extend({}, a.jgrid.ajaxOptions, f.p.ajaxSelectOptions || {}));
                            a.isFunction(f.p.beforeEditCell) && f.p.beforeEditCell.call(f, f.rows[c].id, g, h, c, e);
                            a(j).html("").append(k).attr("tabindex", "0");
                            window.setTimeout(function () {
                                a(k).focus()
                            }, 0);
                            a("input, select, textarea", j).bind("keydown", function (m) {
                                if (m.keyCode === 27) if (a("input.hasDatepicker", j).length > 0) a(".ui-datepicker").is(":hidden") ? a(f).jqGrid("restoreCell", c, e) : a("input.hasDatepicker", j).datepicker("hide");
                                else a(f).jqGrid("restoreCell", c, e);
                                m.keyCode === 13 && a(f).jqGrid("saveCell", c, e);
                                if (m.keyCode == 9) if (f.grid.hDiv.loading) return false;
                                else m.shiftKey ? a(f).jqGrid("prevCell", c, e) : a(f).jqGrid("nextCell", c, e);
                                m.stopPropagation()
                            });
                            a.isFunction(f.p.afterEditCell) && f.p.afterEditCell.call(f, f.rows[c].id, g, h, c, e)
                        } else {
                            if (parseInt(f.p.iCol, 10) >= 0 && parseInt(f.p.iRow, 10) >= 0) {
                                a("td:eq(" + f.p.iCol + ")", f.rows[f.p.iRow]).removeClass("edit-cell ui-state-highlight");
                                a(f.rows[f.p.iRow]).removeClass("selected-row ui-state-hover")
                            }
                            j.addClass("edit-cell ui-state-highlight");
                            a(f.rows[c]).addClass("selected-row ui-state-hover");
                            if (a.isFunction(f.p.onSelectCell)) {
                                h = j.html().replace(/\&#160\;/ig, "");
                                f.p.onSelectCell.call(f, f.rows[c].id, g, h, c, e)
                            }
                        }
                        f.p.iCol =
                        e;
                        f.p.iRow = c
                    }
                }
            })
        },
        saveCell: function (c, e) {
            return this.each(function () {
                var b = this,
                    f;
                if (!(!b.grid || b.p.cellEdit !== true)) {
                    f = b.p.savedRow.length >= 1 ? 0 : null;
                    if (f !== null) {
                        var g = a("td:eq(" + e + ")", b.rows[c]),
                            h, j, d = b.p.colModel[e],
                            l = d.name,
                            k = a.jgrid.jqID(l);
                        switch (d.edittype) {
                        case "select":
                            if (d.editoptions.multiple) {
                                k = a("#" + c + "_" + k, b.rows[c]);
                                var m = [];
                                if (h = a(k).val()) h.join(",");
                                else h = "";
                                a("option:selected", k).each(function (r, C) {
                                    m[r] = a(C).text()
                                });
                                j = m.join(",")
                            } else {
                                h = a("#" + c + "_" + k + ">option:selected", b.rows[c]).val();
                                j = a("#" + c + "_" + k + ">option:selected", b.rows[c]).text()
                            }
                            if (d.formatter) j = h;
                            break;
                        case "checkbox":
                            var q = ["Yes", "No"];
                            if (d.editoptions) q = d.editoptions.value.split(":");
                            j = h = a("#" + c + "_" + k, b.rows[c]).attr("checked") ? q[0] : q[1];
                            break;
                        case "password":
                        case "text":
                        case "textarea":
                        case "button":
                            j = h = a("#" + c + "_" + k, b.rows[c]).val();
                            break;
                        case "custom":
                            try {
                                if (d.editoptions && a.isFunction(d.editoptions.custom_value)) {
                                    h = d.editoptions.custom_value.call(b, a(".customelement", g), "get");
                                    if (h === undefined) throw "e2";
                                    else j = h
                                } else
                                throw "e1";
                            } catch (p) {
                                p == "e1" && a.jgrid.info_dialog(jQuery.jgrid.errors.errcap, "function 'custom_value' " + a.jgrid.edit.msg.nodefined, jQuery.jgrid.edit.bClose);
                                p == "e2" ? a.jgrid.info_dialog(jQuery.jgrid.errors.errcap, "function 'custom_value' " + a.jgrid.edit.msg.novalue, jQuery.jgrid.edit.bClose) : a.jgrid.info_dialog(jQuery.jgrid.errors.errcap, p.message, jQuery.jgrid.edit.bClose)
                            }
                            break
                        }
                        if (j != b.p.savedRow[f].v) {
                            if (a.isFunction(b.p.beforeSaveCell)) if (f = b.p.beforeSaveCell.call(b, b.rows[c].id, l, h, c, e)) j = h = f;
                            var u = a.jgrid.checkValues(h, e, b);
                            if (u[0] === true) {
                                f = {};
                                if (a.isFunction(b.p.beforeSubmitCell))(f = b.p.beforeSubmitCell.call(b, b.rows[c].id, l, h, c, e)) || (f = {});
                                a("input.hasDatepicker", g).length > 0 && a("input.hasDatepicker", g).datepicker("hide");
                                if (b.p.cellsubmit == "remote") if (b.p.cellurl) {
                                    var s = {};
                                    if (b.p.autoencode) h = a.jgrid.htmlEncode(h);
                                    s[l] = h;
                                    q = b.p.prmNames;
                                    d = q.id;
                                    k = q.oper;
                                    s[d] = b.rows[c].id;
                                    s[k] = q.editoper;
                                    s = a.extend(f, s);
                                    a("#lui_" + b.p.id).show();
                                    b.grid.hDiv.loading = true;
                                    a.ajax(a.extend({
                                        url: b.p.cellurl,
                                        data: a.isFunction(b.p.serializeCellData) ? b.p.serializeCellData.call(b, s) : s,
                                        type: "POST",
                                        complete: function (r, C) {
                                            a("#lui_" + b.p.id).hide();
                                            b.grid.hDiv.loading = false;
                                            if (C == "success") if (a.isFunction(b.p.afterSubmitCell)) {
                                                r = b.p.afterSubmitCell.call(b, r, s.id, l, h, c, e);
                                                if (r[0] === true) {
                                                    a(g).empty();
                                                    a(b).jqGrid("setCell", b.rows[c].id, e, j, false, false, true);
                                                    a(g).addClass("dirty-cell");
                                                    a(b.rows[c]).addClass("edited");
                                                    a.isFunction(b.p.afterSaveCell) && b.p.afterSaveCell.call(b, b.rows[c].id, l, h, c, e);
                                                    b.p.savedRow.splice(0, 1)
                                                } else {
                                                    a.jgrid.info_dialog(a.jgrid.errors.errcap, r[1], a.jgrid.edit.bClose);
                                                    a(b).jqGrid("restoreCell", c, e)
                                                }
                                            } else {
                                                a(g).empty();
                                                a(b).jqGrid("setCell", b.rows[c].id, e, j, false, false, true);
                                                a(g).addClass("dirty-cell");
                                                a(b.rows[c]).addClass("edited");
                                                a.isFunction(b.p.afterSaveCell) && b.p.afterSaveCell.call(b, b.rows[c].id, l, h, c, e);
                                                b.p.savedRow.splice(0, 1)
                                            }
                                        },
                                        error: function (r, C) {
                                            a("#lui_" + b.p.id).hide();
                                            b.grid.hDiv.loading = false;
                                            a.isFunction(b.p.errorCell) ? b.p.errorCell.call(b, r, C) : a.jgrid.info_dialog(a.jgrid.errors.errcap, r.status + " : " + r.statusText + "<br/>" + C, a.jgrid.edit.bClose);
                                            a(b).jqGrid("restoreCell", c, e)
                                        }
                                    }, a.jgrid.ajaxOptions, b.p.ajaxCellOptions || {}))
                                } else
                                try {
                                    a.jgrid.info_dialog(a.jgrid.errors.errcap, a.jgrid.errors.nourl, a.jgrid.edit.bClose);
                                    a(b).jqGrid("restoreCell", c, e)
                                } catch (n) {}
                                if (b.p.cellsubmit == "clientArray") {
                                    a(g).empty();
                                    a(b).jqGrid("setCell", b.rows[c].id, e, j, false, false, true);
                                    a(g).addClass("dirty-cell");
                                    a(b.rows[c]).addClass("edited");
                                    a.isFunction(b.p.afterSaveCell) && b.p.afterSaveCell.call(b, b.rows[c].id, l, h, c, e);
                                    b.p.savedRow.splice(0, 1)
                                }
                            } else
                            try {
                                window.setTimeout(function () {
                                    a.jgrid.info_dialog(a.jgrid.errors.errcap, h + " " + u[1], a.jgrid.edit.bClose)
                                }, 100);
                                a(b).jqGrid("restoreCell", c, e)
                            } catch (o) {}
                        } else a(b).jqGrid("restoreCell", c, e)
                    }
                    a.browser.opera ? a("#" + b.p.knv).attr("tabindex", "-1").focus() : window.setTimeout(function () {
                        a("#" + b.p.knv).attr("tabindex", "-1").focus()
                    }, 0)
                }
            })
        },
        restoreCell: function (c, e) {
            return this.each(function () {
                var b = this,
                    f;
                if (!(!b.grid || b.p.cellEdit !== true)) {
                    f = b.p.savedRow.length >= 1 ? 0 : null;
                    if (f !== null) {
                        var g = a("td:eq(" + e + ")", b.rows[c]);
                        if (a.isFunction(a.fn.datepicker)) try {
                            a("input.hasDatepicker", g).datepicker("hide")
                        } catch (h) {}
                        a(g).empty().attr("tabindex", "-1");
                        a(b).jqGrid("setCell", b.rows[c].id, e, b.p.savedRow[f].v, false, false, true);
                        a.isFunction(b.p.afterRestoreCell) && b.p.afterRestoreCell.call(b, b.rows[c].id, b.p.savedRow[f].v, c, e);
                        b.p.savedRow.splice(0, 1)
                    }
                    window.setTimeout(function () {
                        a("#" + b.p.knv).attr("tabindex", "-1").focus()
                    }, 0)
                }
            })
        },
        nextCell: function (c, e) {
            return this.each(function () {
                var b = this,
                    f = false;
                if (!(!b.grid || b.p.cellEdit !== true)) {
                    for (var g = e + 1; g < b.p.colModel.length; g++) if (b.p.colModel[g].editable === true) {
                        f = g;
                        break
                    }
                    if (f !== false) a(b).jqGrid("editCell", c, f, true);
                    else b.p.savedRow.length > 0 && a(b).jqGrid("saveCell", c, e)
                }
            })
        },
        prevCell: function (c, e) {
            return this.each(function () {
                var b = this,
                    f = false;
                if (!(!b.grid || b.p.cellEdit !== true)) {
                    for (var g = e - 1; g >= 0; g--) if (b.p.colModel[g].editable === true) {
                        f = g;
                        break
                    }
                    if (f !== false) a(b).jqGrid("editCell", c, f, true);
                    else b.p.savedRow.length > 0 && a(b).jqGrid("saveCell", c, e)
                }
            })
        },
        GridNav: function () {
            return this.each(function () {
                function c(j, d, l) {
                    if (l.substr(0, 1) == "v") {
                        var k = a(b.grid.bDiv)[0].clientHeight,
                            m = a(b.grid.bDiv)[0].scrollTop,
                            q = b.rows[j].offsetTop + b.rows[j].clientHeight,
                            p = b.rows[j].offsetTop;
                        if (l == "vd") if (q >= k) a(b.grid.bDiv)[0].scrollTop = a(b.grid.bDiv)[0].scrollTop + b.rows[j].clientHeight;
                        if (l == "vu") if (p < m) a(b.grid.bDiv)[0].scrollTop = a(b.grid.bDiv)[0].scrollTop - b.rows[j].clientHeight
                    }
                    if (l == "h") {
                        l = a(b.grid.bDiv)[0].clientWidth;
                        k = a(b.grid.bDiv)[0].scrollLeft;
                        m = b.rows[j].cells[d].offsetLeft;
                        if (b.rows[j].cells[d].offsetLeft + b.rows[j].cells[d].clientWidth >= l + parseInt(k, 10)) a(b.grid.bDiv)[0].scrollLeft = a(b.grid.bDiv)[0].scrollLeft + b.rows[j].cells[d].clientWidth;
                        else if (m < k) a(b.grid.bDiv)[0].scrollLeft = a(b.grid.bDiv)[0].scrollLeft - b.rows[j].cells[d].clientWidth
                    }
                }
                function e(j, d) {
                    var l, k;
                    if (d == "lft") {
                        l = j + 1;
                        for (k = j; k >= 0; k--) if (b.p.colModel[k].hidden !== true) {
                            l = k;
                            break
                        }
                    }
                    if (d == "rgt") {
                        l = j - 1;
                        for (k = j; k < b.p.colModel.length; k++) if (b.p.colModel[k].hidden !== true) {
                            l = k;
                            break
                        }
                    }
                    return l
                }
                var b = this;
                if (!(!b.grid || b.p.cellEdit !== true)) {
                    b.p.knv = b.p.id + "_kn";
                    var f = a("<span style='width:0px;height:0px;background-color:black;' tabindex='0'><span tabindex='-1' style='width:0px;height:0px;background-color:grey' id='" + b.p.knv + "'></span></span>"),
                        g, h;
                    a(f).insertBefore(b.grid.cDiv);
                    a("#" + b.p.knv).focus().keydown(function (j) {
                        h = j.keyCode;
                        if (b.p.direction == "rtl") if (h == 37) h = 39;
                        else if (h == 39) h = 37;
                        switch (h) {
                        case 38:
                            if (b.p.iRow - 1 > 0) {
                                c(b.p.iRow - 1, b.p.iCol, "vu");
                                a(b).jqGrid("editCell", b.p.iRow - 1, b.p.iCol, false)
                            }
                            break;
                        case 40:
                            if (b.p.iRow + 1 <= b.rows.length - 1) {
                                c(b.p.iRow + 1, b.p.iCol, "vd");
                                a(b).jqGrid("editCell", b.p.iRow + 1, b.p.iCol, false)
                            }
                            break;
                        case 37:
                            if (b.p.iCol - 1 >= 0) {
                                g = e(b.p.iCol - 1, "lft");
                                c(b.p.iRow, g, "h");
                                a(b).jqGrid("editCell", b.p.iRow, g, false)
                            }
                            break;
                        case 39:
                            if (b.p.iCol + 1 <= b.p.colModel.length - 1) {
                                g = e(b.p.iCol + 1, "rgt");
                                c(b.p.iRow, g, "h");
                                a(b).jqGrid("editCell", b.p.iRow, g, false)
                            }
                            break;
                        case 13:
                            parseInt(b.p.iCol, 10) >= 0 && parseInt(b.p.iRow, 10) >= 0 && a(b).jqGrid("editCell", b.p.iRow, b.p.iCol, true);
                            break
                        }
                        return false
                    })
                }
            })
        },
        getChangedCells: function (c) {
            var e = [];
            c || (c = "all");
            this.each(function () {
                var b = this,
                    f;
                !b.grid || b.p.cellEdit !== true || a(b.rows).each(function (g) {
                    var h = {};
                    if (a(this).hasClass("edited")) {
                        a("td", this).each(function (j) {
                            f =
                            b.p.colModel[j].name;
                            if (f !== "cb" && f !== "subgrid") if (c == "dirty") {
                                if (a(this).hasClass("dirty-cell")) try {
                                    h[f] = a.unformat(this, {
                                        rowId: b.rows[g].id,
                                        colModel: b.p.colModel[j]
                                    }, j)
                                } catch (d) {
                                    h[f] = a.jgrid.htmlDecode(a(this).html())
                                }
                            } else
                            try {
                                h[f] = a.unformat(this, {
                                    rowId: b.rows[g].id,
                                    colModel: b.p.colModel[j]
                                }, j)
                            } catch (l) {
                                h[f] = a.jgrid.htmlDecode(a(this).html())
                            }
                        });
                        h.id = this.id;
                        e.push(h)
                    }
                })
            });
            return e
        }
    })
})(jQuery);
(function (a) {
    a.extend(a.jgrid, {
        showModal: function (c) {
            c.w.show()
        },
        closeModal: function (c) {
            c.w.hide().attr("aria-hidden", "true");
            c.o && c.o.remove()
        },
        hideModal: function (c, e) {
            e = a.extend({
                jqm: true,
                gb: ""
            }, e || {});
            if (e.onClose) {
                var b = e.onClose(c);
                if (typeof b == "boolean" && !b) return
            }
            if (a.fn.jqm && e.jqm === true) a(c).attr("aria-hidden", "true").jqmHide();
            else {
                if (e.gb !== "") try {
                    a(".jqgrid-overlay:first", e.gb).hide()
                } catch (f) {}
                a(c).hide().attr("aria-hidden", "true")
            }
        },
        findPos: function (c) {
            var e = 0,
                b = 0;
            if (c.offsetParent) {
                do {
                    e += c.offsetLeft;
                    b += c.offsetTop
                } while (c = c.offsetParent)
            }
            return [e, b]
        },
        createModal: function (c, e, b, f, g, h) {
            var j = document.createElement("div"),
                d, l = this;
            d = a(b.gbox).attr("dir") == "rtl" ? true : false;
            j.className = "ui-widget ui-widget-content ui-corner-all ui-jqdialog";
            j.id = c.themodal;
            var k = document.createElement("div");
            k.className = "ui-jqdialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix";
            k.id = c.modalhead;
            a(k).append("<span class='ui-jqdialog-title'>" + b.caption + "</span>");
            var m = a("<a href='javascript:void(0)' class='ui-jqdialog-titlebar-close ui-corner-all'></a>").hover(function () {
                m.addClass("ui-state-hover")
            }, function () {
                m.removeClass("ui-state-hover")
            }).append("<span class='ui-icon ui-icon-closethick'></span>");
            a(k).append(m);
            if (d) {
                j.dir = "rtl";
                a(".ui-jqdialog-title", k).css("float", "right");
                a(".ui-jqdialog-titlebar-close", k).css("left", "0.3em")
            } else {
                j.dir = "ltr";
                a(".ui-jqdialog-title", k).css("float", "left");
                a(".ui-jqdialog-titlebar-close", k).css("right", "0.3em")
            }
            var q = document.createElement("div");
            a(q).addClass("ui-jqdialog-content ui-widget-content").attr("id", c.modalcontent);
            a(q).append(e);
            j.appendChild(q);
            a(j).prepend(k);
            h === true ? a("body").append(j) : a(j).insertBefore(f);
            if (typeof b.jqModal === "undefined") b.jqModal = true;
            e = {};
            if (a.fn.jqm && b.jqModal === true) {
                if (b.left === 0 && b.top === 0) {
                    q = [];
                    q = this.findPos(g);
                    b.left = q[0] + 4;
                    b.top = q[1] + 4
                }
                e.top = b.top + "px";
                e.left = b.left
            } else if (b.left !== 0 || b.top !== 0) {
                e.left = b.left;
                e.top = b.top + "px"
            }
            a("a.ui-jqdialog-titlebar-close", k).click(function () {
                var s = a("#" + c.themodal).data("onClose") || b.onClose,
                    n = a("#" + c.themodal).data("gbox") || b.gbox;
                l.hideModal("#" + c.themodal, {
                    gb: n,
                    jqm: b.jqModal,
                    onClose: s
                });
                return false
            });
            if (b.width === 0 || !b.width) b.width = 300;
            if (b.height === 0 || !b.height) b.height = 200;
            if (!b.zIndex) {
                f = a(f).parents("*[role=dialog]").first().css("z-index");
                b.zIndex = f ? parseInt(f) + 1 : 950
            }
            f = 0;
            if (d && e.left && !h) {
                f = a(b.gbox).width() - (!isNaN(b.width) ? parseInt(b.width, 10) : 0) - 8;
                e.left = parseInt(e.left, 10) + parseInt(f, 10)
            }
            if (e.left) e.left += "px";
            a(j).css(a.extend({
                width: isNaN(b.width) ? "auto" : b.width + "px",
                height: isNaN(b.height) ? "auto" : b.height + "px",
                zIndex: b.zIndex,
                overflow: "hidden"
            }, e)).attr({
                tabIndex: "-1",
                role: "dialog",
                "aria-labelledby": c.modalhead,
                "aria-hidden": "true"
            });
            if (typeof b.drag == "undefined") b.drag = true;
            if (typeof b.resize == "undefined") b.resize = true;
            if (b.drag) {
                a(k).css("cursor", "move");
                if (a.fn.jqDrag) a(j).jqDrag(k);
                else
                try {
                    a(j).draggable({
                        handle: a("#" + k.id)
                    })
                } catch (p) {}
            }
            if (b.resize) if (a.fn.jqResize) {
                a(j).append("<div class='jqResize ui-resizable-handle ui-resizable-se ui-icon ui-icon-gripsmall-diagonal-se ui-icon-grip-diagonal-se'></div>");
                a("#" + c.themodal).jqResize(".jqResize", c.scrollelm ? "#" + c.scrollelm : false)
            } else
            try {
                a(j).resizable({
                    handles: "se, sw",
                    alsoResize: c.scrollelm ? "#" + c.scrollelm : false
                })
            } catch (u) {}
            b.closeOnEscape === true && a(j).keydown(function (s) {
                if (s.which == 27) {
                    s = a("#" + c.themodal).data("onClose") || b.onClose;
                    l.hideModal(this, {
                        gb: b.gbox,
                        jqm: b.jqModal,
                        onClose: s
                    })
                }
            })
        },
        viewModal: function (c, e) {
            e = a.extend({
                toTop: true,
                overlay: 10,
                modal: false,
                onShow: this.showModal,
                onHide: this.closeModal,
                gbox: "",
                jqm: true,
                jqM: true
            }, e || {});
            if (a.fn.jqm && e.jqm === true) e.jqM ? a(c).attr("aria-hidden", "false").jqm(e).jqmShow() : a(c).attr("aria-hidden", "false").jqmShow();
            else {
                if (e.gbox !== "") {
                    a(".jqgrid-overlay:first", e.gbox).show();
                    a(c).data("gbox", e.gbox)
                }
                a(c).show().attr("aria-hidden", "false");
                try {
                    a(":input:visible", c)[0].focus()
                } catch (b) {}
            }
        },
        info_dialog: function (c, e, b, f) {
            var g = {
                width: 290,
                height: "auto",
                dataheight: "auto",
                drag: true,
                resize: false,
                caption: "<b>" + c + "</b>",
                left: 250,
                top: 170,
                zIndex: 1E3,
                jqModal: true,
                modal: false,
                closeOnEscape: true,
                align: "center",
                buttonalign: "center",
                buttons: []
            };
            a.extend(g, f || {});
            var h = g.jqModal,
                j =
                this;
            if (a.fn.jqm && !h) h = false;
            c = "";
            if (g.buttons.length > 0) for (f = 0; f < g.buttons.length; f++) {
                if (typeof g.buttons[f].id == "undefined") g.buttons[f].id = "info_button_" + f;
                c += "<a href='javascript:void(0)' id='" + g.buttons[f].id + "' class='fm-button ui-state-default ui-corner-all'>" + g.buttons[f].text + "</a>"
            }
            f = isNaN(g.dataheight) ? g.dataheight : g.dataheight + "px";
            var d = "<div id='info_id'>";
            d += "<div id='infocnt' style='margin:0px;padding-bottom:1em;width:100%;overflow:auto;position:relative;height:" + f + ";" + ("text-align:" + g.align + ";") + "'>" + e + "</div>";
            d += b ? "<div class='ui-widget-content ui-helper-clearfix' style='text-align:" + g.buttonalign + ";padding-bottom:0.8em;padding-top:0.5em;background-image: none;border-width: 1px 0 0 0;'><a href='javascript:void(0)' id='closedialog' class='fm-button ui-state-default ui-corner-all'>" + b + "</a>" + c + "</div>" : c !== "" ? "<div class='ui-widget-content ui-helper-clearfix' style='text-align:" + g.buttonalign + ";padding-bottom:0.8em;padding-top:0.5em;background-image: none;border-width: 1px 0 0 0;'>" + c + "</div>" : "";
            d += "</div>";
            try {
                a("#info_dialog").attr("aria-hidden") == "false" && this.hideModal("#info_dialog", {
                    jqm: h
                });
                a("#info_dialog").remove()
            } catch (l) {}
            this.createModal({
                themodal: "info_dialog",
                modalhead: "info_head",
                modalcontent: "info_content",
                scrollelm: "infocnt"
            }, d, g, "", "", true);
            c && a.each(g.buttons, function (m) {
                a("#" + this.id, "#info_id").bind("click", function () {
                    g.buttons[m].onClick.call(a("#info_dialog"));
                    return false
                })
            });
            a("#closedialog", "#info_id").click(function () {
                j.hideModal("#info_dialog", {
                    jqm: h
                });
                return false
            });
            a(".fm-button", "#info_dialog").hover(function () {
                a(this).addClass("ui-state-hover")
            }, function () {
                a(this).removeClass("ui-state-hover")
            });
            a.isFunction(g.beforeOpen) && g.beforeOpen();
            this.viewModal("#info_dialog", {
                onHide: function (m) {
                    m.w.hide().remove();
                    m.o && m.o.remove()
                },
                modal: g.modal,
                jqm: h
            });
            a.isFunction(g.afterOpen) && g.afterOpen();
            try {
                a("#info_dialog").focus()
            } catch (k) {}
        },
        createEl: function (c, e, b, f, g) {
            function h(s, n) {
                if (a.isFunction(n.dataInit)) {
                    s.id = n.id;
                    n.dataInit(s);
                    delete n.id;
                    delete n.dataInit
                }
                if (n.dataEvents) {
                    a.each(n.dataEvents, function () {
                        this.data !== undefined ? a(s).bind(this.type, this.data, this.fn) : a(s).bind(this.type, this.fn)
                    });
                    delete n.dataEvents
                }
                return n
            }
            var j = "";
            e.defaultValue && delete e.defaultValue;
            switch (c) {
            case "textarea":
                j = document.createElement("textarea");
                if (f) e.cols || a(j).css({
                    width: "98%"
                });
                else if (!e.cols) e.cols = 20;
                if (!e.rows) e.rows = 2;
                if (b == "&nbsp;" || b == "&#160;" || b.length == 1 && b.charCodeAt(0) == 160) b = "";
                j.value = b;
                e = h(j, e);
                a(j).attr(e).attr({
                    role: "textbox",
                    multiline: "true"
                });
                break;
            case "checkbox":
                j = document.createElement("input");
                j.type = "checkbox";
                if (e.value) {
                    var d = e.value.split(":");
                    if (b === d[0]) {
                        j.checked = true;
                        j.defaultChecked = true
                    }
                    j.value = d[0];
                    a(j).attr("offval", d[1]);
                    try {
                        delete e.value
                    } catch (l) {}
                } else {
                    d = b.toLowerCase();
                    if (d.search(/(false|0|no|off|undefined)/i) < 0 && d !== "") {
                        j.checked = true;
                        j.defaultChecked = true;
                        j.value = b
                    } else j.value = "on";
                    a(j).attr("offval", "off")
                }
                e = h(j, e);
                a(j).attr(e).attr("role", "checkbox");
                break;
            case "select":
                j = document.createElement("select");
                j.setAttribute("role", "select");
                var k, m = [];
                if (e.multiple === true) {
                    k = true;
                    j.multiple = "multiple";
                    a(j).attr("aria-multiselectable", "true")
                } else k = false;
                if (typeof e.dataUrl != "undefined") a.ajax(a.extend({
                    url: e.dataUrl,
                    type: "GET",
                    dataType: "html",
                    success: function (s) {
                        try {
                            delete e.dataUrl;
                            delete e.value
                        } catch (n) {}
                        if (typeof e.buildSelect != "undefined") {
                            s = e.buildSelect(s);
                            s = a(s).html();
                            delete e.buildSelect
                        } else s = a(s).html();
                        if (s) {
                            a(j).append(s);
                            e = h(j, e);
                            if (typeof e.size === "undefined") e.size = k ? 3 : 1;
                            if (k) {
                                m = b.split(",");
                                m = a.map(m, function (o) {
                                    return a.trim(o)
                                })
                            } else m[0] =
                            a.trim(b);
                            a(j).attr(e);
                            setTimeout(function () {
                                a("option", j).each(function (o) {
                                    if (o === 0) this.selected = "";
                                    a(this).attr("role", "option");
                                    if (a.inArray(a.trim(a(this).text()), m) > -1 || a.inArray(a.trim(a(this).val()), m) > -1) {
                                        this.selected = "selected";
                                        if (!k) return false
                                    }
                                })
                            }, 0)
                        }
                    }
                }, g || {}));
                else if (e.value) {
                    if (k) {
                        m = b.split(",");
                        m = a.map(m, function (s) {
                            return a.trim(s)
                        });
                        if (typeof e.size === "undefined") e.size = 3
                    } else e.size = 1;
                    if (typeof e.value === "function") e.value = e.value();
                    if (typeof e.value === "string") {
                        f = e.value.split(";");
                        for (d = 0; d < f.length; d++) {
                            g = f[d].split(":");
                            if (g.length > 2) g[1] = a.map(g, function (s, n) {
                                if (n > 0) return s
                            }).join(":");
                            c = document.createElement("option");
                            c.setAttribute("role", "option");
                            c.value = g[0];
                            c.innerHTML = g[1];
                            if (!k && (a.trim(g[0]) == a.trim(b) || a.trim(g[1]) == a.trim(b))) c.selected = "selected";
                            if (k && (a.inArray(a.trim(g[1]), m) > -1 || a.inArray(a.trim(g[0]), m) > -1)) c.selected = "selected";
                            j.appendChild(c)
                        }
                    } else if (typeof e.value === "object") {
                        f = e.value;
                        for (d in f) if (f.hasOwnProperty(d)) {
                            c = document.createElement("option");
                            c.setAttribute("role", "option");
                            c.value = d;
                            c.innerHTML = f[d];
                            if (!k && (a.trim(d) == a.trim(b) || a.trim(f[d]) == a.trim(b))) c.selected = "selected";
                            if (k && (a.inArray(a.trim(f[d]), m) > -1 || a.inArray(a.trim(d), m) > -1)) c.selected = "selected";
                            j.appendChild(c)
                        }
                    }
                    e = h(j, e);
                    try {
                        delete e.value
                    } catch (q) {}
                    a(j).attr(e)
                }
                break;
            case "text":
            case "password":
            case "button":
                d = c == "button" ? "button" : "textbox";
                j = document.createElement("input");
                j.type = c;
                j.value = b;
                e = h(j, e);
                if (c != "button") if (f) e.size || a(j).css({
                    width: "98%"
                });
                else if (!e.size) e.size =
                20;
                a(j).attr(e).attr("role", d);
                break;
            case "image":
            case "file":
                j = document.createElement("input");
                j.type = c;
                e = h(j, e);
                a(j).attr(e);
                break;
            case "custom":
                j = document.createElement("span");
                try {
                    if (a.isFunction(e.custom_element)) {
                        var p = e.custom_element.call(this, b, e);
                        if (p) {
                            p = a(p).addClass("customelement").attr({
                                id: e.id,
                                name: e.name
                            });
                            a(j).empty().append(p)
                        } else
                        throw "e2";
                    } else
                    throw "e1";
                } catch (u) {
                    u == "e1" && this.info_dialog(a.jgrid.errors.errcap, "function 'custom_element' " + a.jgrid.edit.msg.nodefined, a.jgrid.edit.bClose);
                    u == "e2" ? this.info_dialog(a.jgrid.errors.errcap, "function 'custom_element' " + a.jgrid.edit.msg.novalue, a.jgrid.edit.bClose) : this.info_dialog(a.jgrid.errors.errcap, typeof u === "string" ? u : u.message, a.jgrid.edit.bClose)
                }
                break
            }
            return j
        },
        checkDate: function (c, e) {
            var b = function (m) {
                return m % 4 === 0 && (m % 100 !== 0 || m % 400 === 0) ? 29 : 28
            },
                f = {},
                g;
            c = c.toLowerCase();
            g = c.indexOf("/") != -1 ? "/" : c.indexOf("-") != -1 ? "-" : c.indexOf(".") != -1 ? "." : "/";
            c = c.split(g);
            e = e.split(g);
            if (e.length != 3) return false;
            g = -1;
            for (var h, j = -1, d = -1, l = 0; l < c.length; l++) {
                h = isNaN(e[l]) ? 0 : parseInt(e[l], 10);
                f[c[l]] = h;
                h = c[l];
                if (h.indexOf("y") != -1) g = l;
                if (h.indexOf("m") != -1) d = l;
                if (h.indexOf("d") != -1) j = l
            }
            h = c[g] == "y" || c[g] == "yyyy" ? 4 : c[g] == "yy" ? 2 : -1;
            l = function (m) {
                for (var q = 1; q <= m; q++) {
                    this[q] = 31;
                    if (q == 4 || q == 6 || q == 9 || q == 11) this[q] = 30;
                    if (q == 2) this[q] = 29
                }
                return this
            }(12);
            var k;
            if (g === -1) return false;
            else {
                k = f[c[g]].toString();
                if (h == 2 && k.length == 1) h = 1;
                if (k.length != h || f[c[g]] === 0 && e[g] != "00") return false
            }
            if (d === -1) return false;
            else {
                k = f[c[d]].toString();
                if (k.length < 1 || f[c[d]] < 1 || f[c[d]] > 12) return false
            }
            if (j === -1) return false;
            else {
                k = f[c[j]].toString();
                if (k.length < 1 || f[c[j]] < 1 || f[c[j]] > 31 || f[c[d]] == 2 && f[c[j]] > b(f[c[g]]) || f[c[j]] > l[f[c[d]]]) return false
            }
            return true
        },
        isEmpty: function (c) {
            return c.match(/^\s+$/) || c === "" ? true : false
        },
        checkTime: function (c) {
            var e = /^(\d{1,2}):(\d{2})([ap]m)?$/;
            if (!this.isEmpty(c)) if (c = c.match(e)) {
                if (c[3]) {
                    if (c[1] < 1 || c[1] > 12) return false
                } else if (c[1] > 23) return false;
                if (c[2] > 59) return false
            } else
            return false;
            return true
        },
        checkValues: function (c, e, b) {
            var f, g, h, j;
            if (typeof e == "string") {
                g = 0;
                for (j = b.p.colModel.length; g < j; g++) if (b.p.colModel[g].name == e) {
                    f = b.p.colModel[g].editrules;
                    e = g;
                    try {
                        h = b.p.colModel[g].formoptions.label
                    } catch (d) {}
                    break
                }
            } else if (e >= 0) f = b.p.colModel[e].editrules;
            if (f) {
                h || (h = b.p.colNames[e]);
                if (f.required === true) if (this.isEmpty(c)) return [false, h + ": " + a.jgrid.edit.msg.required, ""];
                g = f.required === false ? false : true;
                if (f.number === true) if (!(g === false && this.isEmpty(c))) if (isNaN(c)) return [false, h + ": " + a.jgrid.edit.msg.number, ""];
                if (typeof f.minValue != "undefined" && !isNaN(f.minValue)) if (parseFloat(c) < parseFloat(f.minValue)) return [false, h + ": " + a.jgrid.edit.msg.minValue + " " + f.minValue, ""];
                if (typeof f.maxValue != "undefined" && !isNaN(f.maxValue)) if (parseFloat(c) > parseFloat(f.maxValue)) return [false, h + ": " + a.jgrid.edit.msg.maxValue + " " + f.maxValue, ""];
                if (f.email === true) if (!(g === false && this.isEmpty(c))) {
                    j = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i;
                    if (!j.test(c)) return [false, h + ": " + a.jgrid.edit.msg.email, ""]
                }
                if (f.integer === true) if (!(g === false && this.isEmpty(c))) {
                    if (isNaN(c)) return [false, h + ": " + a.jgrid.edit.msg.integer, ""];
                    if (c % 1 !== 0 || c.indexOf(".") != -1) return [false, h + ": " + a.jgrid.edit.msg.integer, ""]
                }
                if (f.date === true) if (!(g === false && this.isEmpty(c))) {
                    e = b.p.colModel[e].formatoptions && b.p.colModel[e].formatoptions.newformat ? b.p.colModel[e].formatoptions.newformat : b.p.colModel[e].datefmt || "Y-m-d";
                    if (!this.checkDate(e, c)) return [false, h + ": " + a.jgrid.edit.msg.date + " - " + e, ""]
                }
                if (f.time === true) if (!(g === false && this.isEmpty(c))) if (!this.checkTime(c)) return [false, h + ": " + a.jgrid.edit.msg.date + " - hh:mm (am/pm)", ""];
                if (f.url === true) if (!(g === false && this.isEmpty(c))) {
                    j = /^(((https?)|(ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;
                    if (!j.test(c)) return [false, h + ": " + a.jgrid.edit.msg.url, ""]
                }
                if (f.custom === true) if (!(g === false && this.isEmpty(c))) if (a.isFunction(f.custom_func)) {
                    c = f.custom_func.call(b, c, h);
                    return a.isArray(c) ? c : [false, a.jgrid.edit.msg.customarray, ""]
                } else
                return [false, a.jgrid.edit.msg.customfcheck, ""]
            }
            return [true, "", ""]
        }
    })
})(jQuery);
(function (a) {
    a.jgrid.extend({
        getColProp: function (c) {
            var e = {},
                b = this[0];
            if (!b.grid) return false;
            b = b.p.colModel;
            for (var f = 0; f < b.length; f++) if (b[f].name == c) {
                e = b[f];
                break
            }
            return e
        },
        setColProp: function (c, e) {
            return this.each(function () {
                if (this.grid) if (e) for (var b = this.p.colModel, f = 0; f < b.length; f++) if (b[f].name == c) {
                    a.extend(this.p.colModel[f], e);
                    break
                }
            })
        },
        sortGrid: function (c, e, b) {
            return this.each(function () {
                var f = this,
                    g = -1;
                if (f.grid) {
                    if (!c) c = f.p.sortname;
                    for (var h = 0; h < f.p.colModel.length; h++) if (f.p.colModel[h].index == c || f.p.colModel[h].name == c) {
                        g = h;
                        break
                    }
                    if (g != -1) {
                        h = f.p.colModel[g].sortable;
                        if (typeof h !== "boolean") h = true;
                        if (typeof e !== "boolean") e = false;
                        h && f.sortData("jqgh_" + c, g, e, b)
                    }
                }
            })
        },
        GridDestroy: function () {
            return this.each(function () {
                if (this.grid) {
                    this.p.pager && a(this.p.pager).remove();
                    var c = this.id;
                    try {
                        a("#gbox_" + c).remove()
                    } catch (e) {}
                }
            })
        },
        GridUnload: function () {
            return this.each(function () {
                if (this.grid) {
                    var c = {
                        id: a(this).attr("id"),
                        cl: a(this).attr("class")
                    };
                    this.p.pager && a(this.p.pager).empty().removeClass("ui-state-default ui-jqgrid-pager corner-bottom");
                    var e = document.createElement("table");
                    a(e).attr({
                        id: c.id
                    });
                    e.className = c.cl;
                    c = this.id;
                    a(e).removeClass("ui-jqgrid-btable");
                    if (a(this.p.pager).parents("#gbox_" + c).length === 1) {
                        a(e).insertBefore("#gbox_" + c).show();
                        a(this.p.pager).insertBefore("#gbox_" + c)
                    } else a(e).insertBefore("#gbox_" + c).show();
                    a("#gbox_" + c).remove()
                }
            })
        },
        setGridState: function (c) {
            return this.each(function () {
                if (this.grid) {
                    var e = this;
                    if (c == "hidden") {
                        a(".ui-jqgrid-bdiv, .ui-jqgrid-hdiv", "#gview_" + e.p.id).slideUp("fast");
                        e.p.pager && a(e.p.pager).slideUp("fast");
                        e.p.toppager && a(e.p.toppager).slideUp("fast");
                        if (e.p.toolbar[0] === true) {
                            e.p.toolbar[1] == "both" && a(e.grid.ubDiv).slideUp("fast");
                            a(e.grid.uDiv).slideUp("fast")
                        }
                        e.p.footerrow && a(".ui-jqgrid-sdiv", "#gbox_" + e.p.id).slideUp("fast");
                        a(".ui-jqgrid-titlebar-close span", e.grid.cDiv).removeClass("ui-icon-circle-triangle-n").addClass("ui-icon-circle-triangle-s");
                        e.p.gridstate = "hidden"
                    } else if (c == "visible") {
                        a(".ui-jqgrid-hdiv, .ui-jqgrid-bdiv", "#gview_" + e.p.id).slideDown("fast");
                        e.p.pager && a(e.p.pager).slideDown("fast");
                        e.p.toppager && a(e.p.toppager).slideDown("fast");
                        if (e.p.toolbar[0] === true) {
                            e.p.toolbar[1] == "both" && a(e.grid.ubDiv).slideDown("fast");
                            a(e.grid.uDiv).slideDown("fast")
                        }
                        e.p.footerrow && a(".ui-jqgrid-sdiv", "#gbox_" + e.p.id).slideDown("fast");
                        a(".ui-jqgrid-titlebar-close span", e.grid.cDiv).removeClass("ui-icon-circle-triangle-s").addClass("ui-icon-circle-triangle-n");
                        e.p.gridstate = "visible"
                    }
                }
            })
        },
        updateGridRows: function (c, e, b) {
            var f, g = false,
                h;
            this.each(function () {
                var j = this,
                    d, l, k, m;
                if (!j.grid) return false;
                e || (e = "id");
                c && c.length > 0 && a(c).each(function () {
                    k = this;
                    if (l = j.rows.namedItem(k[e])) {
                        m = k[e];
                        if (b === true) if (j.p.jsonReader.repeatitems === true) {
                            if (j.p.jsonReader.cell) k = k[j.p.jsonReader.cell];
                            for (var q = 0; q < k.length; q++) {
                                d = j.formatter(m, k[q], q, k, "edit");
                                h = j.p.colModel[q].title ? {
                                    title: a.jgrid.stripHtml(d)
                                } : {};
                                j.p.treeGrid === true && f == j.p.ExpandColumn ? a("td:eq(" + q + ") > span:first", l).html(d).attr(h) : a("td:eq(" + q + ")", l).html(d).attr(h)
                            }
                            return g = true
                        }
                        a(j.p.colModel).each(function (p) {
                            f = b === true ? this.jsonmap || this.name : this.name;
                            if (k[f] !== undefined) {
                                d = j.formatter(m, k[f], p, k, "edit");
                                h = this.title ? {
                                    title: a.jgrid.stripHtml(d)
                                } : {};
                                j.p.treeGrid === true && f == j.p.ExpandColumn ? a("td:eq(" + p + ") > span:first", l).html(d).attr(h) : a("td:eq(" + p + ")", l).html(d).attr(h);
                                g = true
                            }
                        })
                    }
                })
            });
            return g
        },
        filterGrid: function (c, e) {
            e = a.extend({
                gridModel: false,
                gridNames: false,
                gridToolbar: false,
                filterModel: [],
                formtype: "horizontal",
                autosearch: true,
                formclass: "filterform",
                tableclass: "filtertable",
                buttonclass: "filterbutton",
                searchButton: "Search",
                clearButton: "Clear",
                enableSearch: false,
                enableClear: false,
                beforeSearch: null,
                afterSearch: null,
                beforeClear: null,
                afterClear: null,
                url: "",
                marksearched: true
            }, e || {});
            return this.each(function () {
                var b = this;
                this.p = e;
                if (this.p.filterModel.length === 0 && this.p.gridModel === false) alert("No filter is set");
                else if (c) {
                    this.p.gridid = c.indexOf("#") != -1 ? c : "#" + c;
                    var f = a(this.p.gridid).jqGrid("getGridParam", "colModel");
                    if (f) {
                        if (this.p.gridModel === true) {
                            var g = a(this.p.gridid)[0],
                                h;
                            a.each(f, function (m) {
                                var q = [];
                                this.search =
                                this.search === false ? false : true;
                                h = this.editrules && this.editrules.searchhidden === true ? true : this.hidden === true ? false : true;
                                if (this.search === true && h === true) {
                                    q.label = b.p.gridNames === true ? g.p.colNames[m] : "";
                                    q.name = this.name;
                                    q.index = this.index || this.name;
                                    q.stype = this.edittype || "text";
                                    if (q.stype != "select") q.stype = "text";
                                    q.defval = this.defval || "";
                                    q.surl = this.surl || "";
                                    q.sopt = this.editoptions || {};
                                    q.width = this.width;
                                    b.p.filterModel.push(q)
                                }
                            })
                        } else a.each(b.p.filterModel, function () {
                            for (var m = 0; m < f.length; m++) if (this.name == f[m].name) {
                                this.index = f[m].index || this.name;
                                break
                            }
                            if (!this.index) this.index = this.name
                        });
                        var j = function () {
                            var m = {},
                                q = 0,
                                p, u = a(b.p.gridid)[0],
                                s;
                            u.p.searchdata = {};
                            a.isFunction(b.p.beforeSearch) && b.p.beforeSearch();
                            a.each(b.p.filterModel, function () {
                                s = this.index;
                                switch (this.stype) {
                                case "select":
                                    if (p = a("select[name=" + s + "]", b).val()) {
                                        m[s] = p;
                                        b.p.marksearched && a("#jqgh_" + this.name, u.grid.hDiv).addClass("dirty-cell");
                                        q++
                                    } else {
                                        b.p.marksearched && a("#jqgh_" + this.name, u.grid.hDiv).removeClass("dirty-cell");
                                        try {
                                            delete u.p.postData[this.index]
                                        } catch (r) {}
                                    }
                                    break;
                                default:
                                    if (p = a("input[name=" + s + "]", b).val()) {
                                        m[s] = p;
                                        b.p.marksearched && a("#jqgh_" + this.name, u.grid.hDiv).addClass("dirty-cell");
                                        q++
                                    } else {
                                        b.p.marksearched && a("#jqgh_" + this.name, u.grid.hDiv).removeClass("dirty-cell");
                                        try {
                                            delete u.p.postData[this.index]
                                        } catch (C) {}
                                    }
                                }
                            });
                            var n = q > 0 ? true : false;
                            a.extend(u.p.postData, m);
                            var o;
                            if (b.p.url) {
                                o = a(u).jqGrid("getGridParam", "url");
                                a(u).jqGrid("setGridParam", {
                                    url: b.p.url
                                })
                            }
                            a(u).jqGrid("setGridParam", {
                                search: n
                            }).trigger("reloadGrid", [{
                                page: 1
                            }]);
                            o && a(u).jqGrid("setGridParam", {
                                url: o
                            });
                            a.isFunction(b.p.afterSearch) && b.p.afterSearch()
                        },
                            d = function () {
                                var m = {},
                                    q, p = 0,
                                    u = a(b.p.gridid)[0],
                                    s;
                                a.isFunction(b.p.beforeClear) && b.p.beforeClear();
                                a.each(b.p.filterModel, function () {
                                    s = this.index;
                                    q = this.defval ? this.defval : "";
                                    if (!this.stype) this.stype = "text";
                                    switch (this.stype) {
                                    case "select":
                                        var r;
                                        a("select[name=" + s + "] option", b).each(function (B) {
                                            if (B === 0) this.selected = true;
                                            if (a(this).text() == q) {
                                                this.selected = true;
                                                r = a(this).val();
                                                return false
                                            }
                                        });
                                        if (r) {
                                            m[s] = r;
                                            b.p.marksearched && a("#jqgh_" + this.name, u.grid.hDiv).addClass("dirty-cell");
                                            p++
                                        } else {
                                            b.p.marksearched && a("#jqgh_" + this.name, u.grid.hDiv).removeClass("dirty-cell");
                                            try {
                                                delete u.p.postData[this.index]
                                            } catch (C) {}
                                        }
                                        break;
                                    case "text":
                                        a("input[name=" + s + "]", b).val(q);
                                        if (q) {
                                            m[s] = q;
                                            b.p.marksearched && a("#jqgh_" + this.name, u.grid.hDiv).addClass("dirty-cell");
                                            p++
                                        } else {
                                            b.p.marksearched && a("#jqgh_" + this.name, u.grid.hDiv).removeClass("dirty-cell");
                                            try {
                                                delete u.p.postData[this.index]
                                            } catch (x) {}
                                        }
                                        break
                                    }
                                });
                                var n = p > 0 ? true : false;
                                a.extend(u.p.postData, m);
                                var o;
                                if (b.p.url) {
                                    o = a(u).jqGrid("getGridParam", "url");
                                    a(u).jqGrid("setGridParam", {
                                        url: b.p.url
                                    })
                                }
                                a(u).jqGrid("setGridParam", {
                                    search: n
                                }).trigger("reloadGrid", [{
                                    page: 1
                                }]);
                                o && a(u).jqGrid("setGridParam", {
                                    url: o
                                });
                                a.isFunction(b.p.afterClear) && b.p.afterClear()
                            },
                            l, k = a("<form name='SearchForm' style=display:inline;' class='" + this.p.formclass + "'></form>");
                        l = a("<table class='" + this.p.tableclass + "' cellspacing='0' cellpading='0' border='0'><tbody></tbody></table>");
                        a(k).append(l);
                        (function () {
                            var m = document.createElement("tr"),
                                q, p, u, s;
                            b.p.formtype == "horizontal" && a(l).append(m);
                            a.each(b.p.filterModel, function (n) {
                                u = document.createElement("td");
                                a(u).append("<label for='" + this.name + "'>" + this.label + "</label>");
                                s = document.createElement("td");
                                var o = this;
                                if (!this.stype) this.stype = "text";
                                switch (this.stype) {
                                case "select":
                                    if (this.surl) a(s).load(this.surl, function () {
                                        o.defval && a("select", this).val(o.defval);
                                        a("select", this).attr({
                                            name: o.index || o.name,
                                            id: "sg_" + o.name
                                        });
                                        o.sopt && a("select", this).attr(o.sopt);
                                        b.p.gridToolbar === true && o.width && a("select", this).width(o.width);
                                        b.p.autosearch === true && a("select", this).change(function () {
                                            j();
                                            return false
                                        })
                                    });
                                    else if (o.sopt.value) {
                                        var r = o.sopt.value,
                                            C = document.createElement("select");
                                        a(C).attr({
                                            name: o.index || o.name,
                                            id: "sg_" + o.name
                                        }).attr(o.sopt);
                                        var x;
                                        if (typeof r === "string") {
                                            n = r.split(";");
                                            for (var B = 0; B < n.length; B++) {
                                                r = n[B].split(":");
                                                x = document.createElement("option");
                                                x.value = r[0];
                                                x.innerHTML = r[1];
                                                if (r[1] == o.defval) x.selected = "selected";
                                                C.appendChild(x)
                                            }
                                        } else if (typeof r === "object") for (B in r) if (r.hasOwnProperty(B)) {
                                            n++;
                                            x = document.createElement("option");
                                            x.value = B;
                                            x.innerHTML = r[B];
                                            if (r[B] == o.defval) x.selected = "selected";
                                            C.appendChild(x)
                                        }
                                        b.p.gridToolbar === true && o.width && a(C).width(o.width);
                                        a(s).append(C);
                                        b.p.autosearch === true && a(C).change(function () {
                                            j();
                                            return false
                                        })
                                    }
                                    break;
                                case "text":
                                    C = this.defval ? this.defval : "";
                                    a(s).append("<input type='text' name='" + (this.index || this.name) + "' id='sg_" + this.name + "' value='" + C + "'/>");
                                    o.sopt && a("input", s).attr(o.sopt);
                                    if (b.p.gridToolbar === true && o.width) a.browser.msie ? a("input", s).width(o.width - 4) : a("input", s).width(o.width - 2);
                                    b.p.autosearch === true && a("input", s).keypress(function (D) {
                                        if ((D.charCode ? D.charCode : D.keyCode ? D.keyCode : 0) == 13) {
                                            j();
                                            return false
                                        }
                                        return this
                                    });
                                    break
                                }
                                if (b.p.formtype == "horizontal") {
                                    b.p.gridToolbar === true && b.p.gridNames === false ? a(m).append(s) : a(m).append(u).append(s);
                                    a(m).append(s)
                                } else {
                                    q = document.createElement("tr");
                                    a(q).append(u).append(s);
                                    a(l).append(q)
                                }
                            });
                            s = document.createElement("td");
                            if (b.p.enableSearch === true) {
                                p = "<input type='button' id='sButton' class='" + b.p.buttonclass + "' value='" + b.p.searchButton + "'/>";
                                a(s).append(p);
                                a("input#sButton", s).click(function () {
                                    j();
                                    return false
                                })
                            }
                            if (b.p.enableClear === true) {
                                p = "<input type='button' id='cButton' class='" + b.p.buttonclass + "' value='" + b.p.clearButton + "'/>";
                                a(s).append(p);
                                a("input#cButton", s).click(function () {
                                    d();
                                    return false
                                })
                            }
                            if (b.p.enableClear === true || b.p.enableSearch === true) if (b.p.formtype == "horizontal") a(m).append(s);
                            else {
                                q = document.createElement("tr");
                                a(q).append("<td>&#160;</td>").append(s);
                                a(l).append(q)
                            }
                        })();
                        a(this).append(k);
                        this.triggerSearch = j;
                        this.clearSearch = d
                    } else alert("Could not get grid colModel")
                } else alert("No target grid is set!")
            })
        },
        filterToolbar: function (c) {
            c = a.extend({
                autosearch: true,
                searchOnEnter: true,
                beforeSearch: null,
                afterSearch: null,
                beforeClear: null,
                afterClear: null,
                searchurl: "",
                stringResult: false,
                groupOp: "AND",
                defaultSearch: "cn" // JM
            }, c || {});
            return this.each(function () {
                function e(j, d) {
                    var l = a(j);
                    l[0] && jQuery.each(d, function () {
                        this.data !== undefined ? l.bind(this.type, this.data, this.fn) : l.bind(this.type, this.fn)
                    })
                }
                var b = this,
                    f = function () {
                        var j = {},
                            d = 0,
                            l, k, m = {},
                            q;
                        a.each(b.p.colModel, function () {
                            k = this.index || this.name;
                            switch (this.stype) {
                            case "select":
                                q = this.searchoptions && this.searchoptions.sopt ? this.searchoptions.sopt[0] : "eq";
                                if (l = a("select[name=" + k + "]", b.grid.hDiv).val()) {
                                    j[k] = l;
                                    m[k] = q;
                                    d++
                                } else
                                try {
                                    delete b.p.postData[k]
                                } catch (r) {}
                                break;
                            case "text":
                                q = this.searchoptions && this.searchoptions.sopt ? this.searchoptions.sopt[0] : c.defaultSearch;
                                if (l = a("input[name=" + k + "]", b.grid.hDiv).val()) {
                                    j[k] = l;
                                    m[k] = q;
                                    d++
                                } else
                                try {
                                    delete b.p.postData[k]
                                } catch (C) {}
                                break
                            }
                        });
                        var p = d > 0 ? true : false;
                        if (c.stringResult === true || b.p.datatype == "local") {
                            var u = '{"groupOp":"' + c.groupOp + '","rules":[',
                                s = 0;
                            a.each(j, function (r, C) {
                                if (s > 0) u += ",";
                                u += '{"field":"' + r + '",';
                                u += '"op":"' + m[r] + '",';
                                u += '"data":"' + C + '"}';
                                s++
                            });
                            u += "]}";
                            a.extend(b.p.postData, {
                                filters: u
                            })
                        } else a.extend(b.p.postData, j);
                        var n;
                        if (b.p.searchurl) {
                            n = b.p.url;
                            a(b).jqGrid("setGridParam", {
                                url: b.p.searchurl
                            })
                        }
                        var o = false;
                        if (a.isFunction(c.beforeSearch)) o = c.beforeSearch.call(b);
                        o || a(b).jqGrid("setGridParam", {
                            search: p
                        }).trigger("reloadGrid", [{
                            page: 1
                        }]);
                        n && a(b).jqGrid("setGridParam", {
                            url: n
                        });
                        a.isFunction(c.afterSearch) && c.afterSearch()
                    },
                    g = a("<tr class='ui-search-toolbar' role='rowheader'></tr>"),
                    h;
                a.each(b.p.colModel, function () {
                    var j = this,
                        d, l, k, m;
                    l = a("<th role='columnheader' class='ui-state-default ui-th-column ui-th-" + b.p.direction + "'></th>");
                    d = a("<div style='width:100%;position:relative;height:100%;padding-right:0.3em;'></div>");
                    this.hidden === true && a(l).css("display", "none");
                    this.search = this.search === false ? false : true;
                    if (typeof this.stype == "undefined") this.stype = "text";
                    k = a.extend({}, this.searchoptions || {});
                    if (this.search) switch (this.stype) {
                    case "select":
                        if (m = this.surl || k.dataUrl) a.ajax(a.extend({
                            url: m,
                            dataType: "html",
                            complete: function (n) {
                                if (k.buildSelect !== undefined)(n = k.buildSelect(n)) && a(d).append(n);
                                else a(d).append(n.responseText);
                                k.defaultValue && a("select", d).val(k.defaultValue);
                                a("select", d).attr({
                                    name: j.index || j.name,
                                    id: "gs_" + j.name
                                });
                                k.attr && a("select", d).attr(k.attr);
                                a("select", d).css({
                                    width: "100%"
                                });
                                k.dataInit !== undefined && k.dataInit(a("select", d)[0]);
                                k.dataEvents !== undefined && e(a("select", d)[0], k.dataEvents);
                                c.autosearch === true && a("select", d).change(function () {
                                    f();
                                    return false
                                });
                                n = null
                            }
                        }, a.jgrid.ajaxOptions, b.p.ajaxSelectOptions || {}));
                        else {
                            var q;
                            if (j.searchoptions && j.searchoptions.value) q = j.searchoptions.value;
                            else if (j.editoptions && j.editoptions.value) q = j.editoptions.value;
                            if (q) {
                                m = document.createElement("select");
                                m.style.width = "100%";
                                a(m).attr({
                                    name: j.index || j.name,
                                    id: "gs_" + j.name
                                });
                                var p, u;
                                if (typeof q === "string") {
                                    q =
                                    q.split(";");
                                    for (var s = 0; s < q.length; s++) {
                                        p = q[s].split(":");
                                        u = document.createElement("option");
                                        u.value = p[0];
                                        u.innerHTML = p[1];
                                        m.appendChild(u)
                                    }
                                } else if (typeof q === "object") for (p in q) if (q.hasOwnProperty(p)) {
                                    u = document.createElement("option");
                                    u.value = p;
                                    u.innerHTML = q[p];
                                    m.appendChild(u)
                                }
                                k.defaultValue && a(m).val(k.defaultValue);
                                k.attr && a(m).attr(k.attr);
                                k.dataInit !== undefined && k.dataInit(m);
                                k.dataEvents !== undefined && e(m, k.dataEvents);
                                a(d).append(m);
                                c.autosearch === true && a(m).change(function () {
                                    f();
                                    return false
                                })
                            }
                        }
                        break;
                    case "text":
                        m = k.defaultValue ? k.defaultValue : "";
                        a(d).append("<input type='text' style='width:95%;padding:0px;' name='" + (j.index || j.name) + "' id='gs_" + j.name + "' value='" + m + "'/>");
                        k.attr && a("input", d).attr(k.attr);
                        k.dataInit !== undefined && k.dataInit(a("input", d)[0]);
                        k.dataEvents !== undefined && e(a("input", d)[0], k.dataEvents);
                        if (c.autosearch === true) c.searchOnEnter ? a("input", d).keypress(function (n) {
                            if ((n.charCode ? n.charCode : n.keyCode ? n.keyCode : 0) == 13) {
                                f();
                                return false
                            }
                            return this
                        }) : a("input", d).keydown(function (n) {
                            switch (n.which) {
                            case 13:
                                return false;
                            case 9:
                            case 16:
                            case 37:
                            case 38:
                            case 39:
                            case 40:
                            case 27:
                                break;
                            default:
                                h && clearTimeout(h);
                                h = setTimeout(function () {
                                    f()
                                }, 500)
                            }
                        });
                        break
                    }
                    a(l).append(d);
                    a(g).append(l)
                });
                a("table thead", b.grid.hDiv).append(g);
                this.triggerToolbar = f;
                this.clearToolbar = function (j) {
                    var d = {},
                        l, k = 0,
                        m;
                    j = typeof j != "boolean" ? true : j;
                    a.each(b.p.colModel, function () {
                        l = this.searchoptions && this.searchoptions.defaultValue ? this.searchoptions.defaultValue : "";
                        m = this.index || this.name;
                        switch (this.stype) {
                        case "select":
                            var o;
                            a("select[name=" + m + "] option", b.grid.hDiv).each(function (x) {
                                if (x === 0) this.selected = true;
                                if (a(this).text() == l) {
                                    this.selected = true;
                                    o = a(this).val();
                                    return false
                                }
                            });
                            if (o) {
                                d[m] = o;
                                k++
                            } else
                            try {
                                delete b.p.postData[m]
                            } catch (r) {}
                            break;
                        case "text":
                            a("input[name=" + m + "]", b.grid.hDiv).val(l);
                            if (l) {
                                d[m] = l;
                                k++
                            } else
                            try {
                                delete b.p.postData[m]
                            } catch (C) {}
                            break
                        }
                    });
                    var q = k > 0 ? true : false;
                    if (c.stringResult === true || b.p.datatype == "local") {
                        var p = '{"groupOp":"' + c.groupOp + '","rules":[',
                            u = 0;
                        a.each(d, function (o, r) {
                            if (u > 0) p += ",";
                            p += '{"field":"' + o + '",';
                            p += '"op":"eq",';
                            p += '"data":"' + r + '"}';
                            u++
                        });
                        p += "]}";
                        a.extend(b.p.postData, {
                            filters: p
                        })
                    } else a.extend(b.p.postData, d);
                    var s;
                    if (b.p.searchurl) {
                        s = b.p.url;
                        a(b).jqGrid("setGridParam", {
                            url: b.p.searchurl
                        })
                    }
                    var n = false;
                    if (a.isFunction(c.beforeClear)) n = c.beforeClear.call(b);
                    n || j && a(b).jqGrid("setGridParam", {
                        search: q
                    }).trigger("reloadGrid", [{
                        page: 1
                    }]);
                    s && a(b).jqGrid("setGridParam", {
                        url: s
                    });
                    a.isFunction(c.afterClear) && c.afterClear()
                };
                this.toggleToolbar = function () {
                    var j = a("tr.ui-search-toolbar", b.grid.hDiv);
                    j.css("display") == "none" ? j.show() : j.hide()
                }
            })
        }
    })
})(jQuery);
(function (a) {
    var c = null;
    a.jgrid.extend({
        searchGrid: function (e) {
            e = a.extend({
                recreateFilter: false,
                drag: true,
                sField: "searchField",
                sValue: "searchString",
                sOper: "searchOper",
                sFilter: "filters",
                loadDefaults: true,
                beforeShowSearch: null,
                afterShowSearch: null,
                onInitializeSearch: null,
                closeAfterSearch: false,
                closeAfterReset: false,
                closeOnEscape: false,
                multipleSearch: false,
                cloneSearchRowOnAdd: true,
                sopt: null,
                stringResult: undefined,
                onClose: null,
                useDataProxy: false,
                overlay: true
            }, a.jgrid.search, e || {});
            return this.each(function () {
                function b(x, B) {
                    B = x.p.postData[B.sFilter];
                    if (typeof B == "string") B = a.jgrid.parse(B);
                    if (B) {
                        B.groupOp && x.SearchFilter.setGroupOp(B.groupOp);
                        if (B.rules) {
                            var D, z = 0,
                                y = B.rules.length;
                            for (D = false; z < y; z++) {
                                D = B.rules[z];
                                if (D.field !== undefined && D.op !== undefined && D.data !== undefined)(D = x.SearchFilter.setFilter({
                                    sfref: x.SearchFilter.$.find(".sf:last"),
                                    filter: a.extend({}, D)
                                })) && x.SearchFilter.add()
                            }
                        }
                    }
                }
                function f(x) {
                    if (e.onClose) {
                        var B = e.onClose(x);
                        if (typeof B == "boolean" && !B) return
                    }
                    x.hide();
                    e.overlay === true && a(".jqgrid-overlay:first", "#gbox_" + d.p.id).hide()
                }
                function g() {
                    var x = a(".ui-searchFilter").length;
                    if (x > 1) {
                        var B = a("#" + l).css("zIndex");
                        a("#" + l).css({
                            zIndex: parseInt(B, 10) + x
                        })
                    }
                    a("#" + l).show();
                    e.overlay === true && a(".jqgrid-overlay:first", "#gbox_" + d.p.id).show();
                    try {
                        a(":input:visible", "#" + l)[0].focus()
                    } catch (D) {}
                }
                function h(x) {
                    var B = x !== undefined,
                        D = a("#" + d.p.id),
                        z = {};
                    if (e.multipleSearch === false) {
                        z[e.sField] = x.rules[0].field;
                        z[e.sValue] = x.rules[0].data;
                        z[e.sOper] = x.rules[0].op
                    } else z[e.sFilter] = x;
                    D[0].p.search = B;
                    a.extend(D[0].p.postData, z);
                    D.trigger("reloadGrid", [{
                        page: 1
                    }]);
                    e.closeAfterSearch && f(a("#" + l))
                }
                function j(x) {
                    x = x && x.hasOwnProperty("reload") ? x.reload : true;
                    var B = a("#" + d.p.id),
                        D = {};
                    B[0].p.search = false;
                    if (e.multipleSearch === false) D[e.sField] = D[e.sValue] = D[e.sOper] = "";
                    else D[e.sFilter] = "";
                    a.extend(B[0].p.postData, D);
                    x && B.trigger("reloadGrid", [{
                        page: 1
                    }]);
                    e.closeAfterReset && f(a("#" + l))
                }
                var d = this;
                if (d.grid) {
                    var l = "fbox_" + d.p.id,
                        k = true;
                    if (a.fn.searchFilter) {
                        e.recreateFilter === true && a("#" + l).remove();
                        if (a("#" + l).html() != null) {
                            if (a.isFunction(e.beforeShowSearch)) {
                                k =
                                e.beforeShowSearch(a("#" + l));
                                if (typeof k == "undefined") k = true
                            }
                            if (k !== false) {
                                g();
                                a.isFunction(e.afterShowSearch) && e.afterShowSearch(a("#" + l))
                            }
                        } else {
                            var m = [],
                                q = a("#" + d.p.id).jqGrid("getGridParam", "colNames"),
                                p = a("#" + d.p.id).jqGrid("getGridParam", "colModel"),
                                // u = ["eq", "ne", "lt", "le", "gt", "ge", "bw", "bn", "in", "ni", "ew", "en", "cn", "nc"], // JM
                                u = ["cn"],
                                s, n, o, r = [];
                            if (e.sopt !== null) for (s = o = 0; s < e.sopt.length; s++) {
                                if ((n = a.inArray(e.sopt[s], u)) != -1) {
                                    r[o] = {
                                        op: e.sopt[s],
                                        text: e.odata[n]
                                    };
                                    o++
                                }
                            } else
                            for (s = 0; s < u.length; s++) r[s] = {
                                op: u[s],
                                text: e.odata[s]
                            };
                            a.each(p, function (x, B) {
                                var D = typeof B.search === "undefined" ? true : B.search,
                                    z = B.hidden === true;
                                x = a.extend({}, {
                                    text: q[x],
                                    itemval: B.index || B.name
                                }, this.searchoptions);
                                B = x.searchhidden === true;
                                if (typeof x.sopt !== "undefined") {
                                    o = 0;
                                    x.ops = [];
                                    if (x.sopt.length > 0) for (s = 0; s < x.sopt.length; s++) if ((n = a.inArray(x.sopt[s], u)) != -1) {
                                        x.ops[o] = {
                                            op: x.sopt[s],
                                            text: e.odata[n]
                                        };
                                        o++
                                    }
                                }
                                if (typeof this.stype === "undefined") this.stype = "text";
                                if (this.stype == "select") if (x.dataUrl === undefined) {
                                    var y;
                                    if (x.value) y = x.value;
                                    else if (this.editoptions) y = this.editoptions.value;
                                    if (y) {
                                        x.dataValues = [];
                                        if (typeof y === "string") {
                                            y = y.split(";");
                                            var A;
                                            for (s = 0; s < y.length; s++) {
                                                A = y[s].split(":");
                                                x.dataValues[s] = {
                                                    value: A[0],
                                                    text: A[1]
                                                }
                                            }
                                        } else if (typeof y === "object") {
                                            s = 0;
                                            for (A in y) if (y.hasOwnProperty(A)) {
                                                x.dataValues[s] = {
                                                    value: A,
                                                    text: y[A]
                                                };
                                                s++
                                            }
                                        }
                                    }
                                }
                                if (B && D || D && !z) m.push(x)
                            });
                            if (m.length > 0) {
                                a("<div id='" + l + "' role='dialog' tabindex='-1'></div>").insertBefore("#gview_" + d.p.id);
                                if (e.stringResult === undefined) e.stringResult = e.multipleSearch;
                                d.SearchFilter = a("#" + l).searchFilter(m, {
                                    groupOps: e.groupOps,
                                    operators: r,
                                    onClose: f,
                                    resetText: e.Reset,
                                    searchText: e.Find,
                                    windowTitle: e.caption,
                                    rulesText: e.rulesText,
                                    matchText: e.matchText,
                                    onSearch: h,
                                    onReset: j,
                                    stringResult: e.stringResult,
                                    ajaxSelectOptions: a.extend({}, a.jgrid.ajaxOptions, d.p.ajaxSelectOptions || {}),
                                    clone: e.cloneSearchRowOnAdd
                                });
                                a(".ui-widget-overlay", "#" + l).remove();
                                d.p.direction == "rtl" && a(".ui-closer", "#" + l).css("float", "left");
                                if (e.drag === true) {
                                    a("#" + l + " table thead tr:first td:first").css("cursor", "move");
                                    if (jQuery.fn.jqDrag) a("#" + l).jqDrag(a("#" + l + " table thead tr:first td:first"));
                                    else
                                    try {
                                        a("#" + l).draggable({
                                            handle: a("#" + l + " table thead tr:first td:first")
                                        })
                                    } catch (C) {}
                                }
                                if (e.multipleSearch === false) {
                                    a(".ui-del, .ui-add, .ui-del, .ui-add-last, .matchText, .rulesText", "#" + l).hide();
                                    a("select[name='groupOp']", "#" + l).hide()
                                }
                                e.multipleSearch === true && e.loadDefaults === true && b(d, e);
                                a.isFunction(e.onInitializeSearch) && e.onInitializeSearch(a("#" + l));
                                if (a.isFunction(e.beforeShowSearch)) {
                                    k = e.beforeShowSearch(a("#" + l));
                                    if (typeof k == "undefined") k = true
                                }
                                if (k !== false) {
                                    g();
                                    a.isFunction(e.afterShowSearch) && e.afterShowSearch(a("#" + l));
                                    e.closeOnEscape === true && a("#" + l).keydown(function (x) {
                                        x.which == 27 && f(a("#" + l));
                                        x.which == 13 && a(".ui-search", this).click()
                                    })
                                }
                            }
                        }
                    }
                }
            })
        },
        editGridRow: function (e, b) {
            c = b = a.extend({
                top: 0,
                left: 0,
                width: 300,
                height: "auto",
                dataheight: "auto",
                modal: false,
                overlay: 10,
                drag: true,
                resize: true,
                url: null,
                mtype: "POST",
                clearAfterAdd: true,
                closeAfterEdit: false,
                reloadAfterSubmit: true,
                onInitializeForm: null,
                beforeInitData: null,
                beforeShowForm: null,
                afterShowForm: null,
                beforeSubmit: null,
                afterSubmit: null,
                onclickSubmit: null,
                afterComplete: null,
                onclickPgButtons: null,
                afterclickPgButtons: null,
                editData: {},
                recreateForm: false,
                jqModal: true,
                closeOnEscape: false,
                addedrow: "first",
                topinfo: "",
                bottominfo: "",
                saveicon: [],
                closeicon: [],
                savekey: [false, 13],
                navkeys: [false, 38, 40],
                checkOnSubmit: false,
                checkOnUpdate: false,
                _savedData: {},
                processing: false,
                onClose: null,
                ajaxEditOptions: {},
                serializeEditData: null,
                viewPagerButtons: true
            }, a.jgrid.edit, b || {});
            return this.each(function () {
                function f() {
                    a(".FormElement", "#" + n).each(function () {
                        var G = a(".customelement", this);
                        if (G.length) {
                            var I = a(G[0]).attr("name");
                            a.each(p.p.colModel, function () {
                                if (this.name == I && this.editoptions && a.isFunction(this.editoptions.custom_value)) {
                                    try {
                                        A[I] = this.editoptions.custom_value(a("#" + I, "#" + n), "get");
                                        if (A[I] === undefined) throw "e1";
                                    } catch (P) {
                                        P == "e1" ? a.jgrid.info_dialog(jQuery.jgrid.errors.errcap, "function 'custom_value' " + a.jgrid.edit.msg.novalue, jQuery.jgrid.edit.bClose) : a.jgrid.info_dialog(jQuery.jgrid.errors.errcap, P.message, jQuery.jgrid.edit.bClose)
                                    }
                                    return true
                                }
                            })
                        } else {
                            switch (a(this).get(0).type) {
                            case "checkbox":
                                if (a(this).attr("checked")) A[this.name] =
                                a(this).val();
                                else {
                                    G = a(this).attr("offval");
                                    A[this.name] = G
                                }
                                break;
                            case "select-one":
                                A[this.name] = a("option:selected", this).val();
                                J[this.name] = a("option:selected", this).text();
                                break;
                            case "select-multiple":
                                A[this.name] = a(this).val();
                                A[this.name] = A[this.name] ? A[this.name].join(",") : "";
                                var V = [];
                                a("option:selected", this).each(function (P, da) {
                                    V[P] = a(da).text()
                                });
                                J[this.name] = V.join(",");
                                break;
                            case "password":
                            case "text":
                            case "textarea":
                            case "button":
                                A[this.name] = a(this).val();
                                break
                            }
                            if (p.p.autoencode) A[this.name] =
                            a.jgrid.htmlEncode(A[this.name])
                        }
                    });
                    return true
                }
                function g(G, I, V, P) {
                    for (var da, S, Q, ga = 0, Z, la, aa, ta = [], ia = false, qa = "", ma = 1; ma <= P; ma++) qa += "<td class='CaptionTD'>&#160;</td><td class='DataTD'>&#160;</td>";
                    if (G != "_empty") ia = a(I).jqGrid("getInd", G);
                    a(I.p.colModel).each(function (pa) {
                        da = this.name;
                        la = (S = this.editrules && this.editrules.edithidden === true ? false : this.hidden === true ? true : false) ? "style='display:none'" : "";
                        if (da !== "cb" && da !== "subgrid" && this.editable === true && da !== "rn") {
                            if (ia === false) Z = "";
                            else if (da == I.p.ExpandColumn && I.p.treeGrid === true) Z = a("td:eq(" + pa + ")", I.rows[ia]).text();
                            else
                            try {
                                Z = a.unformat(a("td:eq(" + pa + ")", I.rows[ia]), {
                                    rowId: G,
                                    colModel: this
                                }, pa)
                            } catch (Fa) {
                                Z = a("td:eq(" + pa + ")", I.rows[ia]).html()
                            }
                            var za = a.extend({}, this.editoptions || {}, {
                                id: da,
                                name: da
                            }),
                                t = a.extend({}, {
                                    elmprefix: "",
                                    elmsuffix: "",
                                    rowabove: false,
                                    rowcontent: ""
                                }, this.formoptions || {}),
                                v = parseInt(t.rowpos, 10) || ga + 1,
                                w = parseInt((parseInt(t.colpos, 10) || 1) * 2, 10);
                            if (G == "_empty" && za.defaultValue) Z = a.isFunction(za.defaultValue) ? za.defaultValue() : za.defaultValue;
                            if (!this.edittype) this.edittype = "text";
                            if (p.p.autoencode) Z = a.jgrid.htmlDecode(Z);
                            aa = a.jgrid.createEl(this.edittype, za, Z, false, a.extend({}, a.jgrid.ajaxOptions, I.p.ajaxSelectOptions || {}));
                            if (Z === "" && this.edittype == "checkbox") Z = a(aa).attr("offval");
                            if (Z === "" && this.edittype == "select") Z = a("option:eq(0)", aa).text();
                            if (c.checkOnSubmit || c.checkOnUpdate) c._savedData[da] = Z;
                            a(aa).addClass("FormElement");
                            if (this.edittype == "text" || this.edittype == "textarea") a(aa).addClass("ui-widget-content ui-corner-all");
                            Q = a(V).find("tr[rowpos=" + v + "]");
                            if (t.rowabove) {
                                za = a("<tr><td class='contentinfo' colspan='" + P * 2 + "'>" + t.rowcontent + "</td></tr>");
                                a(V).append(za);
                                za[0].rp = v
                            }
                            if (Q.length === 0) {
                                Q = a("<tr " + la + " rowpos='" + v + "'></tr>").addClass("FormData").attr("id", "tr_" + da);
                                a(Q).append(qa);
                                a(V).append(Q);
                                Q[0].rp = v
                            }
                            a("td:eq(" + (w - 2) + ")", Q[0]).html(typeof t.label === "undefined" ? I.p.colNames[pa] : t.label);
                            a("td:eq(" + (w - 1) + ")", Q[0]).append(t.elmprefix).append(aa).append(t.elmsuffix);
                            ta[ga] = pa;
                            ga++
                        }
                    });
                    if (ga > 0) {
                        ma = a("<tr class='FormData' style='display:none'><td class='CaptionTD'></td><td colspan='" + (P * 2 - 1) + "' class='DataTD'><input class='FormElement' id='id_g' type='text' name='" + I.p.id + "_id' value='" + G + "'/></td></tr>");
                        ma[0].rp = ga + 999;
                        a(V).append(ma);
                        if (c.checkOnSubmit || c.checkOnUpdate) c._savedData[I.p.id + "_id"] = G
                    }
                    return ta
                }
                function h(G, I, V) {
                    var P, da = 0,
                        S, Q, ga, Z, la;
                    if (c.checkOnSubmit || c.checkOnUpdate) {
                        c._savedData = {};
                        c._savedData[I.p.id + "_id"] = G
                    }
                    var aa = I.p.colModel;
                    if (G == "_empty") {
                        a(aa).each(function () {
                            P = this.name;
                            ga = a.extend({}, this.editoptions || {});
                            Q = a("#" + a.jgrid.jqID(P), "#" + V);
                            if (Q[0] != null) {
                                Z = "";
                                if (ga.defaultValue) {
                                    Z = a.isFunction(ga.defaultValue) ? ga.defaultValue() : ga.defaultValue;
                                    if (Q[0].type == "checkbox") {
                                        la = Z.toLowerCase();
                                        if (la.search(/(false|0|no|off|undefined)/i) < 0 && la !== "") {
                                            Q[0].checked = true;
                                            Q[0].defaultChecked = true;
                                            Q[0].value = Z
                                        } else Q.attr({
                                            checked: "",
                                            defaultChecked: ""
                                        })
                                    } else Q.val(Z)
                                } else if (Q[0].type == "checkbox") {
                                    Q[0].checked = false;
                                    Q[0].defaultChecked = false;
                                    Z = a(Q).attr("offval")
                                } else if (Q[0].type && Q[0].type.substr(0, 6) == "select") Q[0].selectedIndex = 0;
                                else Q.val(Z);
                                if (c.checkOnSubmit === true || c.checkOnUpdate) c._savedData[P] = Z
                            }
                        });
                        a("#id_g", "#" + V).val(G)
                    } else {
                        var ta = a(I).jqGrid("getInd", G, true);
                        if (ta) {
                            a("td", ta).each(function (ia) {
                                P = aa[ia].name;
                                if (P !== "cb" && P !== "subgrid" && P !== "rn" && aa[ia].editable === true) {
                                    if (P == I.p.ExpandColumn && I.p.treeGrid === true) S = a(this).text();
                                    else
                                    try {
                                        S = a.unformat(a(this), {
                                            rowId: G,
                                            colModel: aa[ia]
                                        }, ia)
                                    } catch (qa) {
                                        S = a(this).html()
                                    }
                                    if (p.p.autoencode) S = a.jgrid.htmlDecode(S);
                                    if (c.checkOnSubmit === true || c.checkOnUpdate) c._savedData[P] = S;
                                    P = a.jgrid.jqID(P);
                                    switch (aa[ia].edittype) {
                                    case "password":
                                    case "text":
                                    case "button":
                                    case "image":
                                        a("#" + P, "#" + V).val(S);
                                        break;
                                    case "textarea":
                                        if (S == "&nbsp;" || S == "&#160;" || S.length == 1 && S.charCodeAt(0) == 160) S = "";
                                        a("#" + P, "#" + V).val(S);
                                        break;
                                    case "select":
                                        var ma = S.split(",");
                                        ma = a.map(ma, function (Fa) {
                                            return a.trim(Fa)
                                        });
                                        a("#" + P + " option", "#" + V).each(function () {
                                            this.selected = !aa[ia].editoptions.multiple && (ma[0] == a.trim(a(this).text()) || ma[0] == a.trim(a(this).val())) ? true : aa[ia].editoptions.multiple ? a.inArray(a.trim(a(this).text()), ma) > -1 || a.inArray(a.trim(a(this).val()), ma) > -1 ? true : false : false
                                        });
                                        break;
                                    case "checkbox":
                                        S += "";
                                        if (aa[ia].editoptions && aa[ia].editoptions.value) if (aa[ia].editoptions.value.split(":")[0] == S) {
                                            a("#" + P, "#" + V).attr("checked", true);
                                            a("#" + P, "#" + V).attr("defaultChecked", true)
                                        } else {
                                            a("#" + P, "#" + V).attr("checked", false);
                                            a("#" + P, "#" + V).attr("defaultChecked", "")
                                        } else {
                                            S = S.toLowerCase();
                                            if (S.search(/(false|0|no|off|undefined)/i) < 0 && S !== "") {
                                                a("#" + P, "#" + V).attr("checked", true);
                                                a("#" + P, "#" + V).attr("defaultChecked", true)
                                            } else {
                                                a("#" + P, "#" + V).attr("checked", false);
                                                a("#" + P, "#" + V).attr("defaultChecked", "")
                                            }
                                        }
                                        break;
                                    case "custom":
                                        try {
                                            if (aa[ia].editoptions && a.isFunction(aa[ia].editoptions.custom_value)) aa[ia].editoptions.custom_value(a("#" + P, "#" + V), "set", S);
                                            else
                                            throw "e1";
                                        } catch (pa) {
                                            pa == "e1" ? a.jgrid.info_dialog(jQuery.jgrid.errors.errcap, "function 'custom_value' " + a.jgrid.edit.msg.nodefined, jQuery.jgrid.edit.bClose) : a.jgrid.info_dialog(jQuery.jgrid.errors.errcap, pa.message, jQuery.jgrid.edit.bClose)
                                        }
                                        break
                                    }
                                    da++
                                }
                            });
                            da > 0 && a("#id_g", "#" + n).val(G)
                        }
                    }
                }
                function j() {
                    var G, I = [true, "", ""],
                        V = {},
                        P = p.p.prmNames,
                        da, S;
                    if (a.isFunction(c.beforeCheckValues)) {
                        var Q =
                        c.beforeCheckValues(A, a("#" + s), A[p.p.id + "_id"] == "_empty" ? P.addoper : P.editoper);
                        if (Q && typeof Q === "object") A = Q
                    }
                    for (var ga in A) if (A.hasOwnProperty(ga)) {
                        I = a.jgrid.checkValues(A[ga], ga, p);
                        if (I[0] === false) break
                    }
                    if (I[0]) {
                        if (a.isFunction(c.onclickSubmit)) V = c.onclickSubmit(c, A) || {};
                        if (a.isFunction(c.beforeSubmit)) I = c.beforeSubmit(A, a("#" + s))
                    }
                    if (I[0] && !c.processing) {
                        c.processing = true;
                        a("#sData", "#" + n + "_2").addClass("ui-state-active");
                        S = P.oper;
                        da = P.id;
                        A[S] = a.trim(A[p.p.id + "_id"]) == "_empty" ? P.addoper : P.editoper;
                        if (A[S] != P.addoper) A[da] = A[p.p.id + "_id"];
                        else if (A[da] === undefined) A[da] = A[p.p.id + "_id"];
                        delete A[p.p.id + "_id"];
                        A = a.extend(A, c.editData, V);
                        V = a.extend({
                            url: c.url ? c.url : a(p).jqGrid("getGridParam", "editurl"),
                            type: c.mtype,
                            data: a.isFunction(c.serializeEditData) ? c.serializeEditData(A) : A,
                            complete: function (Z, la) {
                                if (la != "success") {
                                    I[0] = false;
                                    I[1] = a.isFunction(c.errorTextFormat) ? c.errorTextFormat(Z) : la + " Status: '" + Z.statusText + "'. Error code: " + Z.status
                                } else if (a.isFunction(c.afterSubmit)) I = c.afterSubmit(Z, A);
                                if (I[0] === false) {
                                    a("#FormError>td", "#" + n).html(I[1]);
                                    a("#FormError", "#" + n).show()
                                } else {
                                    a.each(p.p.colModel, function () {
                                        if (J[this.name] && this.formatter && this.formatter == "select") try {
                                            delete J[this.name]
                                        } catch (ia) {}
                                    });
                                    A = a.extend(A, J);
                                    p.p.autoencode && a.each(A, function (ia, qa) {
                                        A[ia] = a.jgrid.htmlDecode(qa)
                                    });
                                    c.reloadAfterSubmit = c.reloadAfterSubmit && p.p.datatype != "local";
                                    if (A[S] == P.addoper) {
                                        I[2] || (I[2] = parseInt(p.p.records, 10) + 1 + "");
                                        A[da] = I[2];
                                        if (c.closeAfterAdd) {
                                            if (c.reloadAfterSubmit) a(p).trigger("reloadGrid");
                                            else {
                                                a(p).jqGrid("addRowData", I[2], A, b.addedrow);
                                                a(p).jqGrid("setSelection", I[2])
                                            }
                                            a.jgrid.hideModal("#" + o.themodal, {
                                                gb: "#gbox_" + u,
                                                jqm: b.jqModal,
                                                onClose: c.onClose
                                            })
                                        } else if (c.clearAfterAdd) {
                                            c.reloadAfterSubmit ? a(p).trigger("reloadGrid") : a(p).jqGrid("addRowData", I[2], A, b.addedrow);
                                            h("_empty", p, s)
                                        } else c.reloadAfterSubmit ? a(p).trigger("reloadGrid") : a(p).jqGrid("addRowData", I[2], A, b.addedrow)
                                    } else {
                                        if (c.reloadAfterSubmit) {
                                            a(p).trigger("reloadGrid");
                                            c.closeAfterEdit || setTimeout(function () {
                                                a(p).jqGrid("setSelection", A[da])
                                            }, 1E3)
                                        } else p.p.treeGrid === true ? a(p).jqGrid("setTreeRow", A[da], A) : a(p).jqGrid("setRowData", A[da], A);
                                        c.closeAfterEdit && a.jgrid.hideModal("#" + o.themodal, {
                                            gb: "#gbox_" + u,
                                            jqm: b.jqModal,
                                            onClose: c.onClose
                                        })
                                    }
                                    if (a.isFunction(c.afterComplete)) {
                                        G = Z;
                                        setTimeout(function () {
                                            c.afterComplete(G, A, a("#" + s));
                                            G = null
                                        }, 500)
                                    }
                                }
                                c.processing = false;
                                if (c.checkOnSubmit || c.checkOnUpdate) {
                                    a("#" + s).data("disabled", false);
                                    if (c._savedData[p.p.id + "_id"] != "_empty") for (var aa in c._savedData) if (A[aa]) c._savedData[aa] = A[aa]
                                }
                                a("#sData", "#" + n + "_2").removeClass("ui-state-active");
                                try {
                                    a(":input:visible", "#" + s)[0].focus()
                                } catch (ta) {}
                            },
                            error: function (Z, la, aa) {
                                a("#FormError>td", "#" + n).html(la + " : " + aa);
                                a("#FormError", "#" + n).show();
                                c.processing = false;
                                a("#" + s).data("disabled", false);
                                a("#sData", "#" + n + "_2").removeClass("ui-state-active")
                            }
                        }, a.jgrid.ajaxOptions, c.ajaxEditOptions);
                        if (!V.url && !c.useDataProxy) if (a.isFunction(p.p.dataProxy)) c.useDataProxy = true;
                        else {
                            I[0] = false;
                            I[1] += " " + a.jgrid.errors.nourl
                        }
                        if (I[0]) c.useDataProxy ? p.p.dataProxy.call(p, V, "set_" + p.p.id) : a.ajax(V)
                    }
                    if (I[0] === false) {
                        a("#FormError>td", "#" + n).html(I[1]);
                        a("#FormError", "#" + n).show()
                    }
                }
                function d(G, I) {
                    var V = false,
                        P;
                    for (P in G) if (G[P] != I[P]) {
                        V = true;
                        break
                    }
                    return V
                }
                function l() {
                    var G = true;
                    a("#FormError", "#" + n).hide();
                    if (c.checkOnUpdate) {
                        A = {};
                        J = {};
                        f();
                        M = a.extend({}, A, J);
                        if (U = d(M, c._savedData)) {
                            a("#" + s).data("disabled", true);
                            a(".confirm", "#" + o.themodal).show();
                            G = false
                        }
                    }
                    return G
                }
                function k() {
                    if (e !== "_empty" && typeof p.p.savedRow !== "undefined" && p.p.savedRow.length > 0 && a.isFunction(a.fn.jqGrid.restoreRow)) for (var G =
                    0; G < p.p.savedRow.length; G++) if (p.p.savedRow[G].id == e) {
                        a(p).jqGrid("restoreRow", e);
                        break
                    }
                }
                function m(G, I) {
                    G === 0 ? a("#pData", "#" + n + "_2").addClass("ui-state-disabled") : a("#pData", "#" + n + "_2").removeClass("ui-state-disabled");
                    G == I ? a("#nData", "#" + n + "_2").addClass("ui-state-disabled") : a("#nData", "#" + n + "_2").removeClass("ui-state-disabled")
                }
                function q() {
                    var G = a(p).jqGrid("getDataIDs"),
                        I = a("#id_g", "#" + n).val();
                    return [a.inArray(I, G), G]
                }
                var p = this;
                if (p.grid && e) {
                    var u = p.p.id,
                        s = "FrmGrid_" + u,
                        n = "TblGrid_" + u,
                        o = {
                            themodal: "editmod" + u,
                            modalhead: "edithd" + u,
                            modalcontent: "editcnt" + u,
                            scrollelm: s
                        },
                        r = a.isFunction(c.beforeShowForm) ? c.beforeShowForm : false,
                        C = a.isFunction(c.afterShowForm) ? c.afterShowForm : false,
                        x = a.isFunction(c.beforeInitData) ? c.beforeInitData : false,
                        B = a.isFunction(c.onInitializeForm) ? c.onInitializeForm : false,
                        D = true,
                        z = 1,
                        y = 0,
                        A, J, M, U;
                    if (e == "new") {
                        e = "_empty";
                        b.caption = c.addCaption
                    } else b.caption = c.editCaption;
                    b.recreateForm === true && a("#" + o.themodal).html() != null && a("#" + o.themodal).remove();
                    var ba = true;
                    if (b.checkOnUpdate && b.jqModal && !b.modal) ba = false;
                    if (a("#" + o.themodal).html() != null) {
                        if (x) {
                            D = x(a("#" + s));
                            if (typeof D == "undefined") D = true
                        }
                        if (D === false) return;
                        k();
                        a(".ui-jqdialog-title", "#" + o.modalhead).html(b.caption);
                        a("#FormError", "#" + n).hide();
                        if (c.topinfo) {
                            a(".topinfo", "#" + n + "_2").html(c.topinfo);
                            a(".tinfo", "#" + n + "_2").show()
                        } else a(".tinfo", "#" + n + "_2").hide();
                        if (c.bottominfo) {
                            a(".bottominfo", "#" + n + "_2").html(c.bottominfo);
                            a(".binfo", "#" + n + "_2").show()
                        } else a(".binfo", "#" + n + "_2").hide();
                        h(e, p, s);
                        e == "_empty" || !c.viewPagerButtons ? a("#pData, #nData", "#" + n + "_2").hide() : a("#pData, #nData", "#" + n + "_2").show();
                        if (c.processing === true) {
                            c.processing = false;
                            a("#sData", "#" + n + "_2").removeClass("ui-state-active")
                        }
                        if (a("#" + s).data("disabled") === true) {
                            a(".confirm", "#" + o.themodal).hide();
                            a("#" + s).data("disabled", false)
                        }
                        r && r(a("#" + s));
                        a("#" + o.themodal).data("onClose", c.onClose);
                        a.jgrid.viewModal("#" + o.themodal, {
                            gbox: "#gbox_" + u,
                            jqm: b.jqModal,
                            jqM: false,
                            overlay: b.overlay,
                            modal: b.modal
                        });
                        ba || a(".jqmOverlay").click(function () {
                            if (!l()) return false;
                            a.jgrid.hideModal("#" + o.themodal, {
                                gb: "#gbox_" + u,
                                jqm: b.jqModal,
                                onClose: c.onClose
                            });
                            return false
                        });
                        C && C(a("#" + s))
                    } else {
                        var W = isNaN(b.dataheight) ? b.dataheight : b.dataheight + "px";
                        W = a("<form name='FormPost' id='" + s + "' class='FormGrid' onSubmit='return false;' style='width:100%;overflow:auto;position:relative;height:" + W + ";'></form>").data("disabled", false);
                        var ca = a("<table id='" + n + "' class='EditTable' cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>");
                        if (x) {
                            D = x(a("#" + s));
                            if (typeof D == "undefined") D =
                            true
                        }
                        if (D === false) return;
                        k();
                        a(p.p.colModel).each(function () {
                            var G = this.formoptions;
                            z = Math.max(z, G ? G.colpos || 0 : 0);
                            y = Math.max(y, G ? G.rowpos || 0 : 0)
                        });
                        a(W).append(ca);
                        x = a("<tr id='FormError' style='display:none'><td class='ui-state-error' colspan='" + z * 2 + "'></td></tr>");
                        x[0].rp = 0;
                        a(ca).append(x);
                        x = a("<tr style='display:none' class='tinfo'><td class='topinfo' colspan='" + z * 2 + "'>" + c.topinfo + "</td></tr>");
                        x[0].rp = 0;
                        a(ca).append(x);
                        D = (x = p.p.direction == "rtl" ? true : false) ? "nData" : "pData";
                        var oa = x ? "pData" : "nData";
                        g(e, p, ca, z);
                        D = "<a href='javascript:void(0)' id='" + D + "' class='fm-button ui-state-default ui-corner-left'><span class='ui-icon ui-icon-triangle-1-w'></span></div>";
                        oa = "<a href='javascript:void(0)' id='" + oa + "' class='fm-button ui-state-default ui-corner-right'><span class='ui-icon ui-icon-triangle-1-e'></span></div>";
                        var wa = "<a href='javascript:void(0)' id='sData' class='fm-button ui-state-default ui-corner-all'>" + b.bSubmit + "</a>",
                            xa = "<a href='javascript:void(0)' id='cData' class='fm-button ui-state-default ui-corner-all'>" + b.bCancel + "</a>";
                        D = "<table border='0' cellspacing='0' cellpadding='0' class='EditTable' id='" + n + "_2'><tbody><tr><td colspan='2'><hr class='ui-widget-content' style='margin:1px'/></td></tr><tr id='Act_Buttons'><td class='navButton'>" + (x ? oa + D : D + oa) + "</td><td class='EditButton'>" + wa + xa + "</td></tr>";
                        D += "<tr style='display:none' class='binfo'><td class='bottominfo' colspan='2'>" + c.bottominfo + "</td></tr>";
                        D += "</tbody></table>";
                        if (y > 0) {
                            var Ba = [];
                            a.each(a(ca)[0].rows, function (G, I) {
                                Ba[G] = I
                            });
                            Ba.sort(function (G, I) {
                                if (G.rp > I.rp) return 1;
                                if (G.rp < I.rp) return -1;
                                return 0
                            });
                            a.each(Ba, function (G, I) {
                                a("tbody", ca).append(I)
                            })
                        }
                        b.gbox = "#gbox_" + u;
                        var Ga = false;
                        if (b.closeOnEscape === true) {
                            b.closeOnEscape = false;
                            Ga = true
                        }
                        W = a("<span></span>").append(W).append(D);
                        a.jgrid.createModal(o, W, b, "#gview_" + p.p.id, a("#gbox_" + p.p.id)[0]);
                        if (x) {
                            a("#pData, #nData", "#" + n + "_2").css("float", "right");
                            a(".EditButton", "#" + n + "_2").css("text-align", "left")
                        }
                        c.topinfo && a(".tinfo", "#" + n + "_2").show();
                        c.bottominfo && a(".binfo", "#" + n + "_2").show();
                        D =
                        W = null;
                        a("#" + o.themodal).keydown(function (G) {
                            var I = G.target;
                            if (a("#" + s).data("disabled") === true) return false;
                            if (c.savekey[0] === true && G.which == c.savekey[1]) if (I.tagName != "TEXTAREA") {
                                a("#sData", "#" + n + "_2").trigger("click");
                                return false
                            }
                            if (G.which === 27) {
                                if (!l()) return false;
                                Ga && a.jgrid.hideModal(this, {
                                    gb: b.gbox,
                                    jqm: b.jqModal,
                                    onClose: c.onClose
                                });
                                return false
                            }
                            if (c.navkeys[0] === true) {
                                if (a("#id_g", "#" + n).val() == "_empty") return true;
                                if (G.which == c.navkeys[1]) {
                                    a("#pData", "#" + n + "_2").trigger("click");
                                    return false
                                }
                                if (G.which == c.navkeys[2]) {
                                    a("#nData", "#" + n + "_2").trigger("click");
                                    return false
                                }
                            }
                        });
                        if (b.checkOnUpdate) {
                            a("a.ui-jqdialog-titlebar-close span", "#" + o.themodal).removeClass("jqmClose");
                            a("a.ui-jqdialog-titlebar-close", "#" + o.themodal).unbind("click").click(function () {
                                if (!l()) return false;
                                a.jgrid.hideModal("#" + o.themodal, {
                                    gb: "#gbox_" + u,
                                    jqm: b.jqModal,
                                    onClose: c.onClose
                                });
                                return false
                            })
                        }
                        b.saveicon = a.extend([true, "left", "ui-icon-disk"], b.saveicon);
                        b.closeicon = a.extend([true, "left", "ui-icon-close"], b.closeicon);
                        if (b.saveicon[0] === true) a("#sData", "#" + n + "_2").addClass(b.saveicon[1] == "right" ? "fm-button-icon-right" : "fm-button-icon-left").append("<span class='ui-icon " + b.saveicon[2] + "'></span>");
                        if (b.closeicon[0] === true) a("#cData", "#" + n + "_2").addClass(b.closeicon[1] == "right" ? "fm-button-icon-right" : "fm-button-icon-left").append("<span class='ui-icon " + b.closeicon[2] + "'></span>");
                        if (c.checkOnSubmit || c.checkOnUpdate) {
                            wa = "<a href='javascript:void(0)' id='sNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>" + b.bYes + "</a>";
                            oa = "<a href='javascript:void(0)' id='nNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>" + b.bNo + "</a>";
                            xa = "<a href='javascript:void(0)' id='cNew' class='fm-button ui-state-default ui-corner-all' style='z-index:1002'>" + b.bExit + "</a>";
                            W = b.zIndex || 999;
                            W++;
                            a("<div class='ui-widget-overlay jqgrid-overlay confirm' style='z-index:" + W + ";display:none;'>&#160;" + (a.browser.msie && a.browser.version == 6 ? '<iframe style="display:block;position:absolute;z-index:-1;filter:Alpha(Opacity=\'0\');" src="javascript:false;"></iframe>' : "") + "</div><div class='confirm ui-widget-content ui-jqconfirm' style='z-index:" + (W + 1) + "'>" + b.saveData + "<br/><br/>" + wa + oa + xa + "</div>").insertAfter("#" + s);
                            a("#sNew", "#" + o.themodal).click(function () {
                                j();
                                a("#" + s).data("disabled", false);
                                a(".confirm", "#" + o.themodal).hide();
                                return false
                            });
                            a("#nNew", "#" + o.themodal).click(function () {
                                a(".confirm", "#" + o.themodal).hide();
                                a("#" + s).data("disabled", false);
                                setTimeout(function () {
                                    a(":input", "#" + s)[0].focus()
                                }, 0);
                                return false
                            });
                            a("#cNew", "#" + o.themodal).click(function () {
                                a(".confirm", "#" + o.themodal).hide();
                                a("#" + s).data("disabled", false);
                                a.jgrid.hideModal("#" + o.themodal, {
                                    gb: "#gbox_" + u,
                                    jqm: b.jqModal,
                                    onClose: c.onClose
                                });
                                return false
                            })
                        }
                        B && B(a("#" + s));
                        e == "_empty" || !c.viewPagerButtons ? a("#pData,#nData", "#" + n + "_2").hide() : a("#pData,#nData", "#" + n + "_2").show();
                        r && r(a("#" + s));
                        a("#" + o.themodal).data("onClose", c.onClose);
                        a.jgrid.viewModal("#" + o.themodal, {
                            gbox: "#gbox_" + u,
                            jqm: b.jqModal,
                            overlay: b.overlay,
                            modal: b.modal
                        });
                        ba || a(".jqmOverlay").click(function () {
                            if (!l()) return false;
                            a.jgrid.hideModal("#" + o.themodal, {
                                gb: "#gbox_" + u,
                                jqm: b.jqModal,
                                onClose: c.onClose
                            });
                            return false
                        });
                        C && C(a("#" + s));
                        a(".fm-button", "#" + o.themodal).hover(function () {
                            a(this).addClass("ui-state-hover")
                        }, function () {
                            a(this).removeClass("ui-state-hover")
                        });
                        a("#sData", "#" + n + "_2").click(function () {
                            A = {};
                            J = {};
                            a("#FormError", "#" + n).hide();
                            f();
                            if (A[p.p.id + "_id"] == "_empty") j();
                            else if (b.checkOnSubmit === true) {
                                M = a.extend({}, A, J);
                                if (U = d(M, c._savedData)) {
                                    a("#" + s).data("disabled", true);
                                    a(".confirm", "#" + o.themodal).show()
                                } else j()
                            } else j();
                            return false
                        });
                        a("#cData", "#" + n + "_2").click(function () {
                            if (!l()) return false;
                            a.jgrid.hideModal("#" + o.themodal, {
                                gb: "#gbox_" + u,
                                jqm: b.jqModal,
                                onClose: c.onClose
                            });
                            return false
                        });
                        a("#nData", "#" + n + "_2").click(function () {
                            if (!l()) return false;
                            a("#FormError", "#" + n).hide();
                            var G = q();
                            G[0] = parseInt(G[0], 10);
                            if (G[0] != -1 && G[1][G[0] + 1]) {
                                a.isFunction(b.onclickPgButtons) && b.onclickPgButtons("next", a("#" + s), G[1][G[0]]);
                                h(G[1][G[0] + 1], p, s);
                                a(p).jqGrid("setSelection", G[1][G[0] + 1]);
                                a.isFunction(b.afterclickPgButtons) && b.afterclickPgButtons("next", a("#" + s), G[1][G[0] + 1]);
                                m(G[0] + 1, G[1].length - 1)
                            }
                            return false
                        });
                        a("#pData", "#" + n + "_2").click(function () {
                            if (!l()) return false;
                            a("#FormError", "#" + n).hide();
                            var G = q();
                            if (G[0] != -1 && G[1][G[0] - 1]) {
                                a.isFunction(b.onclickPgButtons) && b.onclickPgButtons("prev", a("#" + s), G[1][G[0]]);
                                h(G[1][G[0] - 1], p, s);
                                a(p).jqGrid("setSelection", G[1][G[0] - 1]);
                                a.isFunction(b.afterclickPgButtons) && b.afterclickPgButtons("prev", a("#" + s), G[1][G[0] - 1]);
                                m(G[0] - 1, G[1].length - 1)
                            }
                            return false
                        })
                    }
                    r = q();
                    m(r[0], r[1].length - 1)
                }
            })
        },
        viewGridRow: function (e, b) {
            b = a.extend({
                top: 0,
                left: 0,
                width: 0,
                height: "auto",
                dataheight: "auto",
                modal: false,
                overlay: 10,
                drag: true,
                resize: true,
                jqModal: true,
                closeOnEscape: false,
                labelswidth: "30%",
                closeicon: [],
                navkeys: [false, 38, 40],
                onClose: null,
                beforeShowForm: null,
                beforeInitData: null,
                viewPagerButtons: true
            }, a.jgrid.view, b || {});
            return this.each(function () {
                function f() {
                    if (b.closeOnEscape === true || b.navkeys[0] === true) setTimeout(function () {
                        a(".ui-jqdialog-titlebar-close", "#" + p.modalhead).focus()
                    }, 0)
                }
                function g(y, A, J, M) {
                    for (var U, ba, W, ca = 0, oa, wa, xa = [], Ba = false, Ga = "<td class='CaptionTD form-view-label ui-widget-content' width='" + b.labelswidth + "'>&#160;</td><td class='DataTD form-view-data ui-helper-reset ui-widget-content'>&#160;</td>", G = "", I = ["integer", "number", "currency"], V = 0, P = 0, da, S, Q, ga = 1; ga <= M; ga++) G += ga == 1 ? Ga : "<td class='CaptionTD form-view-label ui-widget-content'>&#160;</td><td class='DataTD form-view-data ui-widget-content'>&#160;</td>";
                    a(A.p.colModel).each(function () {
                        ba = this.editrules && this.editrules.edithidden === true ? false : this.hidden === true ? true : false;
                        if (!ba && this.align === "right") if (this.formatter && a.inArray(this.formatter, I) !== -1) V = Math.max(V, parseInt(this.width, 10));
                        else P = Math.max(P, parseInt(this.width, 10))
                    });
                    da = V !== 0 ? V : P !== 0 ? P : 0;
                    Ba = a(A).jqGrid("getInd", y);
                    a(A.p.colModel).each(function (Z) {
                        U = this.name;
                        S = false;
                        wa = (ba = this.editrules && this.editrules.edithidden === true ? false : this.hidden === true ? true : false) ? "style='display:none'" : "";
                        Q = typeof this.viewable != "boolean" ? true : this.viewable;
                        if (U !== "cb" && U !== "subgrid" && U !== "rn" && Q) {
                            oa = Ba === false ? "" : U == A.p.ExpandColumn && A.p.treeGrid === true ? a("td:eq(" + Z + ")", A.rows[Ba]).text() : a("td:eq(" + Z + ")", A.rows[Ba]).html();
                            S = this.align === "right" && da !== 0 ? true : false;
                            a.extend({}, this.editoptions || {}, {
                                id: U,
                                name: U
                            });
                            var la = a.extend({}, {
                                rowabove: false,
                                rowcontent: ""
                            }, this.formoptions || {}),
                                aa = parseInt(la.rowpos, 10) || ca + 1,
                                ta = parseInt((parseInt(la.colpos, 10) || 1) * 2, 10);
                            if (la.rowabove) {
                                var ia = a("<tr><td class='contentinfo' colspan='" + M * 2 + "'>" + la.rowcontent + "</td></tr>");
                                a(J).append(ia);
                                ia[0].rp = aa
                            }
                            W = a(J).find("tr[rowpos=" + aa + "]");
                            if (W.length === 0) {
                                W = a("<tr " + wa + " rowpos='" + aa + "'></tr>").addClass("FormData").attr("id", "trv_" + U);
                                a(W).append(G);
                                a(J).append(W);
                                W[0].rp = aa
                            }
                            a("td:eq(" + (ta - 2) + ")", W[0]).html("<b>" + (typeof la.label === "undefined" ? A.p.colNames[Z] : la.label) + "</b>");
                            a("td:eq(" + (ta - 1) + ")", W[0]).append("<span>" + oa + "</span>").attr("id", "v_" + U);
                            S && a("td:eq(" + (ta - 1) + ") span", W[0]).css({
                                "text-align": "right",
                                width: da + "px"
                            });
                            xa[ca] = Z;
                            ca++
                        }
                    });
                    if (ca > 0) {
                        y = a("<tr class='FormData' style='display:none'><td class='CaptionTD'></td><td colspan='" + (M * 2 - 1) + "' class='DataTD'><input class='FormElement' id='id_g' type='text' name='id' value='" + y + "'/></td></tr>");
                        y[0].rp = ca + 99;
                        a(J).append(y)
                    }
                    return xa
                }
                function h(y, A) {
                    var J, M, U = 0,
                        ba, W;
                    if (W = a(A).jqGrid("getInd", y, true)) {
                        a("td", W).each(function (ca) {
                            J = A.p.colModel[ca].name;
                            M = A.p.colModel[ca].editrules && A.p.colModel[ca].editrules.edithidden === true ? false : A.p.colModel[ca].hidden === true ? true : false;
                            if (J !== "cb" && J !== "subgrid" && J !== "rn") {
                                ba = J == A.p.ExpandColumn && A.p.treeGrid === true ? a(this).text() : a(this).html();
                                a.extend({}, A.p.colModel[ca].editoptions || {});
                                J = a.jgrid.jqID("v_" + J);
                                a("#" + J + " span", "#" + q).html(ba);
                                M && a("#" + J, "#" + q).parents("tr:first").hide();
                                U++
                            }
                        });
                        U > 0 && a("#id_g", "#" + q).val(y)
                    }
                }
                function j(y, A) {
                    y === 0 ? a("#pData", "#" + q + "_2").addClass("ui-state-disabled") : a("#pData", "#" + q + "_2").removeClass("ui-state-disabled");
                    y == A ? a("#nData", "#" + q + "_2").addClass("ui-state-disabled") : a("#nData", "#" + q + "_2").removeClass("ui-state-disabled")
                }
                function d() {
                    var y = a(l).jqGrid("getDataIDs"),
                        A = a("#id_g", "#" + q).val();
                    return [a.inArray(A, y), y]
                }
                var l = this;
                if (l.grid && e) {
                    if (!b.imgpath) b.imgpath = l.p.imgpath;
                    var k = l.p.id,
                        m = "ViewGrid_" + k,
                        q = "ViewTbl_" + k,
                        p = {
                            themodal: "viewmod" + k,
                            modalhead: "viewhd" + k,
                            modalcontent: "viewcnt" + k,
                            scrollelm: m
                        },
                        u = a.isFunction(b.beforeInitData) ? b.beforeInitData : false,
                        s = true,
                        n = 1,
                        o = 0;
                    if (a("#" + p.themodal).html() != null) {
                        if (u) {
                            s = u(a("#" + m));
                            if (typeof s == "undefined") s = true
                        }
                        if (s === false) return;
                        a(".ui-jqdialog-title", "#" + p.modalhead).html(b.caption);
                        a("#FormError", "#" + q).hide();
                        h(e, l);
                        a.isFunction(b.beforeShowForm) && b.beforeShowForm(a("#" + m));
                        a.jgrid.viewModal("#" + p.themodal, {
                            gbox: "#gbox_" + k,
                            jqm: b.jqModal,
                            jqM: false,
                            overlay: b.overlay,
                            modal: b.modal
                        });
                        f()
                    } else {
                        var r = isNaN(b.dataheight) ? b.dataheight : b.dataheight + "px";
                        r = a("<form name='FormPost' id='" + m + "' class='FormGrid' style='width:100%;overflow:auto;position:relative;height:" + r + ";'></form>");
                        var C = a("<table id='" + q + "' class='EditTable' cellspacing='1' cellpadding='2' border='0' style='table-layout:fixed'><tbody></tbody></table>");
                        if (u) {
                            s = u(a("#" + m));
                            if (typeof s == "undefined") s = true
                        }
                        if (s === false) return;
                        a(l.p.colModel).each(function () {
                            var y = this.formoptions;
                            n = Math.max(n, y ? y.colpos || 0 : 0);
                            o = Math.max(o, y ? y.rowpos || 0 : 0)
                        });
                        a(r).append(C);
                        g(e, l, C, n);
                        u = l.p.direction == "rtl" ? true : false;
                        s = "<a href='javascript:void(0)' id='" + (u ? "nData" : "pData") + "' class='fm-button ui-state-default ui-corner-left'><span class='ui-icon ui-icon-triangle-1-w'></span></a>";
                        var x = "<a href='javascript:void(0)' id='" + (u ? "pData" : "nData") + "' class='fm-button ui-state-default ui-corner-right'><span class='ui-icon ui-icon-triangle-1-e'></span></a>",
                            B = "<a href='javascript:void(0)' id='cData' class='fm-button ui-state-default ui-corner-all'>" + b.bClose + "</a>";
                        if (o > 0) {
                            var D = [];
                            a.each(a(C)[0].rows, function (y, A) {
                                D[y] = A
                            });
                            D.sort(function (y, A) {
                                if (y.rp > A.rp) return 1;
                                if (y.rp < A.rp) return -1;
                                return 0
                            });
                            a.each(D, function (y, A) {
                                a("tbody", C).append(A)
                            })
                        }
                        b.gbox = "#gbox_" + k;
                        var z = false;
                        if (b.closeOnEscape === true) {
                            b.closeOnEscape = false;
                            z = true
                        }
                        r = a("<span></span>").append(r).append("<table border='0' class='EditTable' id='" + q + "_2'><tbody><tr id='Act_Buttons'><td class='navButton' width='" + b.labelswidth + "'>" + (u ? x + s : s + x) + "</td><td class='EditButton'>" + B + "</td></tr></tbody></table>");
                        a.jgrid.createModal(p, r, b, "#gview_" + l.p.id, a("#gview_" + l.p.id)[0]);
                        if (u) {
                            a("#pData, #nData", "#" + q + "_2").css("float", "right");
                            a(".EditButton", "#" + q + "_2").css("text-align", "left")
                        }
                        b.viewPagerButtons || a("#pData, #nData", "#" + q + "_2").hide();
                        r = null;
                        a("#" + p.themodal).keydown(function (y) {
                            if (y.which === 27) {
                                z && a.jgrid.hideModal(this, {
                                    gb: b.gbox,
                                    jqm: b.jqModal,
                                    onClose: b.onClose
                                });
                                return false
                            }
                            if (b.navkeys[0] === true) {
                                if (y.which === b.navkeys[1]) {
                                    a("#pData", "#" + q + "_2").trigger("click");
                                    return false
                                }
                                if (y.which === b.navkeys[2]) {
                                    a("#nData", "#" + q + "_2").trigger("click");
                                    return false
                                }
                            }
                        });
                        b.closeicon = a.extend([true, "left", "ui-icon-close"], b.closeicon);
                        if (b.closeicon[0] === true) a("#cData", "#" + q + "_2").addClass(b.closeicon[1] == "right" ? "fm-button-icon-right" : "fm-button-icon-left").append("<span class='ui-icon " + b.closeicon[2] + "'></span>");
                        a.isFunction(b.beforeShowForm) && b.beforeShowForm(a("#" + m));
                        a.jgrid.viewModal("#" + p.themodal, {
                            gbox: "#gbox_" + k,
                            jqm: b.jqModal,
                            modal: b.modal
                        });
                        a(".fm-button:not(.ui-state-disabled)", "#" + q + "_2").hover(function () {
                            a(this).addClass("ui-state-hover")
                        }, function () {
                            a(this).removeClass("ui-state-hover")
                        });
                        f();
                        a("#cData", "#" + q + "_2").click(function () {
                            a.jgrid.hideModal("#" + p.themodal, {
                                gb: "#gbox_" + k,
                                jqm: b.jqModal,
                                onClose: b.onClose
                            });
                            return false
                        });
                        a("#nData", "#" + q + "_2").click(function () {
                            a("#FormError", "#" + q).hide();
                            var y = d();
                            y[0] = parseInt(y[0], 10);
                            if (y[0] != -1 && y[1][y[0] + 1]) {
                                a.isFunction(b.onclickPgButtons) && b.onclickPgButtons("next", a("#" + m), y[1][y[0]]);
                                h(y[1][y[0] + 1], l);
                                a(l).jqGrid("setSelection", y[1][y[0] + 1]);
                                a.isFunction(b.afterclickPgButtons) && b.afterclickPgButtons("next", a("#" + m), y[1][y[0] + 1]);
                                j(y[0] + 1, y[1].length - 1)
                            }
                            f();
                            return false
                        });
                        a("#pData", "#" + q + "_2").click(function () {
                            a("#FormError", "#" + q).hide();
                            var y = d();
                            if (y[0] != -1 && y[1][y[0] - 1]) {
                                a.isFunction(b.onclickPgButtons) && b.onclickPgButtons("prev", a("#" + m), y[1][y[0]]);
                                h(y[1][y[0] - 1], l);
                                a(l).jqGrid("setSelection", y[1][y[0] - 1]);
                                a.isFunction(b.afterclickPgButtons) && b.afterclickPgButtons("prev", a("#" + m), y[1][y[0] - 1]);
                                j(y[0] - 1, y[1].length - 1)
                            }
                            f();
                            return false
                        })
                    }
                    r = d();
                    j(r[0], r[1].length - 1)
                }
            })
        },
        delGridRow: function (e, b) {
            c = b = a.extend({
                top: 0,
                left: 0,
                width: 240,
                height: "auto",
                dataheight: "auto",
                modal: false,
                overlay: 10,
                drag: true,
                resize: true,
                url: "",
                mtype: "POST",
                reloadAfterSubmit: true,
                beforeShowForm: null,
                beforeInitData: null,
                afterShowForm: null,
                beforeSubmit: null,
                onclickSubmit: null,
                afterSubmit: null,
                jqModal: true,
                closeOnEscape: false,
                delData: {},
                delicon: [],
                cancelicon: [],
                onClose: null,
                ajaxDelOptions: {},
                processing: false,
                serializeDelData: null,
                useDataProxy: false
            }, a.jgrid.del, b || {});
            return this.each(function () {
                var f = this;
                if (f.grid) if (e) {
                    var g = typeof b.beforeShowForm === "function" ? true : false,
                        h = typeof b.afterShowForm === "function" ? true : false,
                        j = a.isFunction(b.beforeInitData) ? b.beforeInitData : false,
                        d = f.p.id,
                        l = {},
                        k = true,
                        m = "DelTbl_" + d,
                        q, p, u, s, n = {
                            themodal: "delmod" + d,
                            modalhead: "delhd" + d,
                            modalcontent: "delcnt" + d,
                            scrollelm: m
                        };
                    if (jQuery.isArray(e)) e = e.join();
                    if (a("#" + n.themodal).html() != null) {
                        if (j) {
                            k = j(a("#" + m));
                            if (typeof k == "undefined") k =
                            true
                        }
                        if (k === false) return;
                        a("#DelData>td", "#" + m).text(e);
                        a("#DelError", "#" + m).hide();
                        if (c.processing === true) {
                            c.processing = false;
                            a("#dData", "#" + m).removeClass("ui-state-active")
                        }
                        g && b.beforeShowForm(a("#" + m));
                        a.jgrid.viewModal("#" + n.themodal, {
                            gbox: "#gbox_" + d,
                            jqm: b.jqModal,
                            jqM: false,
                            overlay: b.overlay,
                            modal: b.modal
                        })
                    } else {
                        var o = isNaN(b.dataheight) ? b.dataheight : b.dataheight + "px";
                        o = "<div id='" + m + "' class='formdata' style='width:100%;overflow:auto;position:relative;height:" + o + ";'>";
                        o += "<table class='DelTable'><tbody>";
                        o += "<tr id='DelError' style='display:none'><td class='ui-state-error'></td></tr>";
                        o += "<tr id='DelData' style='display:none'><td >" + e + "</td></tr>";
                        o += '<tr><td class="delmsg" style="white-space:pre;">' + b.msg + "</td></tr><tr><td >&#160;</td></tr>";
                        o += "</tbody></table></div>";
                        o += "<table cellspacing='0' cellpadding='0' border='0' class='EditTable' id='" + m + "_2'><tbody><tr><td><hr class='ui-widget-content' style='margin:1px'/></td></tr></tr><tr><td class='DelButton EditButton'>" + ("<a href='javascript:void(0)' id='dData' class='fm-button ui-state-default ui-corner-all'>" + b.bSubmit + "</a>") + "&#160;" + ("<a href='javascript:void(0)' id='eData' class='fm-button ui-state-default ui-corner-all'>" + b.bCancel + "</a>") + "</td></tr></tbody></table>";
                        b.gbox = "#gbox_" + d;
                        a.jgrid.createModal(n, o, b, "#gview_" + f.p.id, a("#gview_" + f.p.id)[0]);
                        if (j) {
                            k = j(a("#" + m));
                            if (typeof k == "undefined") k = true
                        }
                        if (k === false) return;
                        a(".fm-button", "#" + m + "_2").hover(function () {
                            a(this).addClass("ui-state-hover")
                        }, function () {
                            a(this).removeClass("ui-state-hover")
                        });
                        b.delicon = a.extend([true, "left", "ui-icon-scissors"], b.delicon);
                        b.cancelicon = a.extend([true, "left", "ui-icon-cancel"], b.cancelicon);
                        if (b.delicon[0] === true) a("#dData", "#" + m + "_2").addClass(b.delicon[1] == "right" ? "fm-button-icon-right" : "fm-button-icon-left").append("<span class='ui-icon " + b.delicon[2] + "'></span>");
                        if (b.cancelicon[0] === true) a("#eData", "#" + m + "_2").addClass(b.cancelicon[1] == "right" ? "fm-button-icon-right" : "fm-button-icon-left").append("<span class='ui-icon " + b.cancelicon[2] + "'></span>");
                        a("#dData", "#" + m + "_2").click(function () {
                            var r = [true, ""];
                            l = {};
                            var C = a("#DelData>td", "#" + m).text();
                            if (typeof b.onclickSubmit === "function") l = b.onclickSubmit(c, C) || {};
                            if (typeof b.beforeSubmit === "function") r = b.beforeSubmit(C);
                            if (r[0] && !c.processing) {
                                c.processing = true;
                                a(this).addClass("ui-state-active");
                                u = f.p.prmNames;
                                q = a.extend({}, c.delData, l);
                                s = u.oper;
                                q[s] = u.deloper;
                                p = u.id;
                                q[p] = C;
                                var x = a.extend({
                                    url: c.url ? c.url : a(f).jqGrid("getGridParam", "editurl"),
                                    type: b.mtype,
                                    data: a.isFunction(b.serializeDelData) ? b.serializeDelData(q) : q,
                                    complete: function (B, D) {
                                        if (D != "success") {
                                            r[0] =
                                            false;
                                            r[1] = a.isFunction(c.errorTextFormat) ? c.errorTextFormat(B) : D + " Status: '" + B.statusText + "'. Error code: " + B.status
                                        } else if (typeof c.afterSubmit === "function") r = c.afterSubmit(B, q);
                                        if (r[0] === false) {
                                            a("#DelError>td", "#" + m).html(r[1]);
                                            a("#DelError", "#" + m).show()
                                        } else {
                                            if (c.reloadAfterSubmit && f.p.datatype != "local") a(f).trigger("reloadGrid");
                                            else {
                                                D = [];
                                                D = C.split(",");
                                                if (f.p.treeGrid === true) try {
                                                    a(f).jqGrid("delTreeNode", D[0])
                                                } catch (z) {} else
                                                for (var y = 0; y < D.length; y++) a(f).jqGrid("delRowData", D[y]);
                                                f.p.selrow =
                                                null;
                                                f.p.selarrrow = []
                                            }
                                            a.isFunction(c.afterComplete) && setTimeout(function () {
                                                c.afterComplete(B, C)
                                            }, 500)
                                        }
                                        c.processing = false;
                                        a("#dData", "#" + m + "_2").removeClass("ui-state-active");
                                        r[0] && a.jgrid.hideModal("#" + n.themodal, {
                                            gb: "#gbox_" + d,
                                            jqm: b.jqModal,
                                            onClose: c.onClose
                                        })
                                    },
                                    error: function (B, D, z) {
                                        a("#DelError>td", "#" + m).html(D + " : " + z);
                                        a("#DelError", "#" + m).show();
                                        c.processing = false;
                                        a("#dData", "#" + m + "_2").removeClass("ui-state-active")
                                    }
                                }, a.jgrid.ajaxOptions, b.ajaxDelOptions);
                                if (!x.url && !c.useDataProxy) if (a.isFunction(f.p.dataProxy)) c.useDataProxy =
                                true;
                                else {
                                    r[0] = false;
                                    r[1] += " " + a.jgrid.errors.nourl
                                }
                                if (r[0]) c.useDataProxy ? f.p.dataProxy.call(f, x, "del_" + f.p.id) : a.ajax(x)
                            }
                            if (r[0] === false) {
                                a("#DelError>td", "#" + m).html(r[1]);
                                a("#DelError", "#" + m).show()
                            }
                            return false
                        });
                        a("#eData", "#" + m + "_2").click(function () {
                            a.jgrid.hideModal("#" + n.themodal, {
                                gb: "#gbox_" + d,
                                jqm: b.jqModal,
                                onClose: c.onClose
                            });
                            return false
                        });
                        g && b.beforeShowForm(a("#" + m));
                        a.jgrid.viewModal("#" + n.themodal, {
                            gbox: "#gbox_" + d,
                            jqm: b.jqModal,
                            overlay: b.overlay,
                            modal: b.modal
                        })
                    }
                    h && b.afterShowForm(a("#" + m));
                    b.closeOnEscape === true && setTimeout(function () {
                        a(".ui-jqdialog-titlebar-close", "#" + n.modalhead).focus()
                    }, 0)
                }
            })
        },
        navGrid: function (e, b, f, g, h, j, d) {
            b = a.extend({
                edit: true,
                editicon: "ui-icon-pencil",
                add: true,
                addicon: "ui-icon-plus",
                del: true,
                delicon: "ui-icon-trash",
                search: true,
                searchicon: "ui-icon-search",
                refresh: true,
                refreshicon: "ui-icon-refresh",
                refreshstate: "firstpage",
                view: false,
                viewicon: "ui-icon-document",
                position: "left",
                closeOnEscape: true,
                beforeRefresh: null,
                afterRefresh: null,
                cloneToTop: false
            }, a.jgrid.nav, b || {});
            return this.each(function () {
                var l = {
                    themodal: "alertmod",
                    modalhead: "alerthd",
                    modalcontent: "alertcnt"
                },
                    k = this,
                    m, q, p;
                if (!(!k.grid || typeof e != "string")) {
                    if (a("#" + l.themodal).html() === null) {
                        if (typeof window.innerWidth != "undefined") {
                            m = window.innerWidth;
                            q = window.innerHeight
                        } else if (typeof document.documentElement != "undefined" && typeof document.documentElement.clientWidth != "undefined" && document.documentElement.clientWidth !== 0) {
                            m = document.documentElement.clientWidth;
                            q = document.documentElement.clientHeight
                        } else {
                            m =
                            1024;
                            q = 768
                        }
                        a.jgrid.createModal(l, "<div>" + b.alerttext + "</div><span tabindex='0'><span tabindex='-1' id='jqg_alrt'></span></span>", {
                            gbox: "#gbox_" + k.p.id,
                            jqModal: true,
                            drag: true,
                            resize: true,
                            caption: b.alertcap,
                            top: q / 2 - 25,
                            left: m / 2 - 100,
                            width: 200,
                            height: "auto",
                            closeOnEscape: b.closeOnEscape
                        }, "", "", true)
                    }
                    m = 1;
                    if (b.cloneToTop && k.p.toppager) m = 2;
                    for (q = 0; q < m; q++) {
                        var u = a("<table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table navtable' style='float:left;table-layout:auto;'><tbody><tr></tr></tbody></table>"),
                            s, n;
                        if (q === 0) {
                            s = e;
                            n = k.p.id;
                            if (s == k.p.toppager) {
                                n += "_top";
                                m = 1
                            }
                        } else {
                            s = k.p.toppager;
                            n = k.p.id + "_top"
                        }
                        k.p.direction == "rtl" && a(u).attr("dir", "rtl").css("float", "right");
                        if (b.add) {
                            g = g || {};
                            p = a("<td class='ui-pg-button ui-corner-all'></td>");
                            a(p).append("<div class='ui-pg-div'><span class='ui-icon " + b.addicon + "'></span>" + b.addtext + "</div>");
                            a("tr", u).append(p);
                            a(p, u).attr({
                                title: b.addtitle || "",
                                id: g.id || "add_" + n
                            }).click(function () {
                                a(this).hasClass("ui-state-disabled") || (typeof b.addfunc == "function" ? b.addfunc() : a(k).jqGrid("editGridRow", "new", g));
                                return false
                            }).hover(function () {
                                a(this).hasClass("ui-state-disabled") || a(this).addClass("ui-state-hover")
                            }, function () {
                                a(this).removeClass("ui-state-hover")
                            });
                            p = null
                        }
                        if (b.edit) {
                            p = a("<td class='ui-pg-button ui-corner-all'></td>");
                            f = f || {};
                            a(p).append("<div class='ui-pg-div'><span class='ui-icon " + b.editicon + "'></span>" + b.edittext + "</div>");
                            a("tr", u).append(p);
                            a(p, u).attr({
                                title: b.edittitle || "",
                                id: f.id || "edit_" + n
                            }).click(function () {
                                if (!a(this).hasClass("ui-state-disabled")) {
                                    var o =
                                    k.p.selrow;
                                    if (o) typeof b.editfunc == "function" ? b.editfunc(o) : a(k).jqGrid("editGridRow", o, f);
                                    else {
                                        a.jgrid.viewModal("#" + l.themodal, {
                                            gbox: "#gbox_" + k.p.id,
                                            jqm: true
                                        });
                                        a("#jqg_alrt").focus()
                                    }
                                }
                                return false
                            }).hover(function () {
                                a(this).hasClass("ui-state-disabled") || a(this).addClass("ui-state-hover")
                            }, function () {
                                a(this).removeClass("ui-state-hover")
                            });
                            p = null
                        }
                        if (b.view) {
                            p = a("<td class='ui-pg-button ui-corner-all'></td>");
                            d = d || {};
                            a(p).append("<div class='ui-pg-div'><span class='ui-icon " + b.viewicon + "'></span>" + b.viewtext + "</div>");
                            a("tr", u).append(p);
                            a(p, u).attr({
                                title: b.viewtitle || "",
                                id: d.id || "view_" + n
                            }).click(function () {
                                if (!a(this).hasClass("ui-state-disabled")) {
                                    var o = k.p.selrow;
                                    if (o) a(k).jqGrid("viewGridRow", o, d);
                                    else {
                                        a.jgrid.viewModal("#" + l.themodal, {
                                            gbox: "#gbox_" + k.p.id,
                                            jqm: true
                                        });
                                        a("#jqg_alrt").focus()
                                    }
                                }
                                return false
                            }).hover(function () {
                                a(this).hasClass("ui-state-disabled") || a(this).addClass("ui-state-hover")
                            }, function () {
                                a(this).removeClass("ui-state-hover")
                            });
                            p = null
                        }
                        if (b.del) {
                            p = a("<td class='ui-pg-button ui-corner-all'></td>");
                            h = h || {};
                            a(p).append("<div class='ui-pg-div'><span class='ui-icon " + b.delicon + "'></span>" + b.deltext + "</div>");
                            a("tr", u).append(p);
                            a(p, u).attr({
                                title: b.deltitle || "",
                                id: h.id || "del_" + n
                            }).click(function () {
                                if (!a(this).hasClass("ui-state-disabled")) {
                                    var o;
                                    if (k.p.multiselect) {
                                        o = k.p.selarrrow;
                                        if (o.length === 0) o = null
                                    } else o = k.p.selrow;
                                    if (o)"function" == typeof b.delfunc ? b.delfunc(o) : a(k).jqGrid("delGridRow", o, h);
                                    else {
                                        a.jgrid.viewModal("#" + l.themodal, {
                                            gbox: "#gbox_" + k.p.id,
                                            jqm: true
                                        });
                                        a("#jqg_alrt").focus()
                                    }
                                }
                                return false
                            }).hover(function () {
                                a(this).hasClass("ui-state-disabled") || a(this).addClass("ui-state-hover")
                            }, function () {
                                a(this).removeClass("ui-state-hover")
                            });
                            p = null
                        }
                        if (b.add || b.edit || b.del || b.view) a("tr", u).append("<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>");
                        if (b.search) {
                            p = a("<td class='ui-pg-button ui-corner-all'></td>");
                            j = j || {};
                            a(p).append("<div class='ui-pg-div'><span class='ui-icon " + b.searchicon + "'></span>" + b.searchtext + "</div>");
                            a("tr", u).append(p);
                            a(p, u).attr({
                                title: b.searchtitle || "",
                                id: j.id || "search_" + n
                            }).click(function () {
                                a(this).hasClass("ui-state-disabled") || a(k).jqGrid("searchGrid", j);
                                return false
                            }).hover(function () {
                                a(this).hasClass("ui-state-disabled") || a(this).addClass("ui-state-hover")
                            }, function () {
                                a(this).removeClass("ui-state-hover")
                            });
                            p = null
                        }
                        if (b.refresh) {
                            p = a("<td class='ui-pg-button ui-corner-all'></td>");
                            a(p).append("<div class='ui-pg-div'><span class='ui-icon " + b.refreshicon + "'></span>" + b.refreshtext + "</div>");
                            a("tr", u).append(p);
                            a(p, u).attr({
                                title: b.refreshtitle || "",
                                id: "refresh_" + n
                            }).click(function () {
                                if (!a(this).hasClass("ui-state-disabled")) {
                                    a.isFunction(b.beforeRefresh) && b.beforeRefresh();
                                    k.p.search = false;
                                    try {
                                        a("#fbox_" + k.p.id).searchFilter().reset({
                                            reload: false
                                        });
                                        a.isFunction(k.clearToolbar) && k.clearToolbar(false)
                                    } catch (o) {}
                                    switch (b.refreshstate) {
                                    case "firstpage":
                                        a(k).trigger("reloadGrid", [{
                                            page: 1
                                        }]);
                                        break;
                                    case "current":
                                        a(k).trigger("reloadGrid", [{
                                            current: true
                                        }]);
                                        break
                                    }
                                    a.isFunction(b.afterRefresh) && b.afterRefresh()
                                }
                                return false
                            }).hover(function () {
                                a(this).hasClass("ui-state-disabled") || a(this).addClass("ui-state-hover")
                            }, function () {
                                a(this).removeClass("ui-state-hover")
                            });
                            p = null
                        }
                        p = a(".ui-jqgrid").css("font-size") || "11px";
                        a("body").append("<div id='testpg2' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:" + p + ";visibility:hidden;' ></div>");
                        p = a(u).clone().appendTo("#testpg2").width();
                        a("#testpg2").remove();
                        a(s + "_" + b.position, s).append(u);
                        if (k.p._nvtd) {
                            if (p > k.p._nvtd[0]) {
                                a(s + "_" + b.position, s).width(p);
                                k.p._nvtd[0] = p
                            }
                            k.p._nvtd[1] = p
                        }
                        u = p = p = null
                    }
                }
            })
        },
        navButtonAdd: function (e, b) {
            b = a.extend({
                caption: "newButton",
                title: "",
                buttonicon: "ui-icon-newwin",
                onClickButton: null,
                position: "last",
                cursor: "pointer"
            }, b || {});
            return this.each(function () {
                if (this.grid) {
                    if (e.indexOf("#") !== 0) e = "#" + e;
                    var f = a(".navtable", e)[0],
                        g = this;
                    if (f) {
                        var h = a("<td></td>");
                        b.buttonicon.toString().toUpperCase() == "NONE" ? a(h).addClass("ui-pg-button ui-corner-all").append("<div class='ui-pg-div'>" + b.caption + "</div>") : a(h).addClass("ui-pg-button ui-corner-all").append("<div class='ui-pg-div'><span class='ui-icon " + b.buttonicon + "'></span>" + b.caption + "</div>");
                        b.id && a(h).attr("id", b.id);
                        if (b.position == "first") f.rows[0].cells.length === 0 ? a("tr", f).append(h) : a("tr td:eq(0)", f).before(h);
                        else a("tr", f).append(h);
                        a(h, f).attr("title", b.title || "").click(function (j) {
                            a(this).hasClass("ui-state-disabled") || a.isFunction(b.onClickButton) && b.onClickButton.call(g, j);
                            return false
                        }).hover(function () {
                            a(this).hasClass("ui-state-disabled") || a(this).addClass("ui-state-hover")
                        }, function () {
                            a(this).removeClass("ui-state-hover")
                        })
                    }
                }
            })
        },
        navSeparatorAdd: function (e, b) {
            b = a.extend({
                sepclass: "ui-separator",
                sepcontent: ""
            }, b || {});
            return this.each(function () {
                if (this.grid) {
                    if (e.indexOf("#") !== 0) e = "#" + e;
                    var f = a(".navtable", e)[0];
                    if (f) {
                        var g = "<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='" + b.sepclass + "'></span>" + b.sepcontent + "</td>";
                        a("tr", f).append(g)
                    }
                }
            })
        },
        GridToForm: function (e, b) {
            return this.each(function () {
                var f = this;
                if (f.grid) {
                    var g = a(f).jqGrid("getRowData", e);
                    if (g) for (var h in g) a("[name=" + h + "]", b).is("input:radio") || a("[name=" + h + "]", b).is("input:checkbox") ? a("[name=" + h + "]", b).each(function () {
                        a(this).val() == g[h] ? a(this).attr("checked", "checked") : a(this).attr("checked", "")
                    }) : a("[name=" + h + "]", b).val(g[h])
                }
            })
        },
        FormToGrid: function (e, b, f, g) {
            return this.each(function () {
                var h = this;
                if (h.grid) {
                    f || (f = "set");
                    g || (g = "first");
                    var j = a(b).serializeArray(),
                        d = {};
                    a.each(j, function (l, k) {
                        d[k.name] = k.value
                    });
                    if (f == "add") a(h).jqGrid("addRowData", e, d, g);
                    else f == "set" && a(h).jqGrid("setRowData", e, d)
                }
            })
        }
    })
})(jQuery);
(function (a) {
    a.jgrid.extend({
        groupingSetup: function () {
            return this.each(function () {
                var c = this,
                    e = c.p.groupingView;
                if (e !== null && (typeof e === "object" || a.isFunction(e))) if (e.groupField.length) {
                    for (var b = 0; b < e.groupField.length; b++) {
                        e.groupOrder[b] || (e.groupOrder[b] = "asc");
                        e.groupText[b] || (e.groupText[b] = "{0}");
                        if (typeof e.groupColumnShow[b] != "boolean") e.groupColumnShow[b] = true;
                        if (typeof e.groupSummary[b] != "boolean") e.groupSummary[b] = false;
                        e.groupColumnShow[b] === true ? a(c).jqGrid("showCol", e.groupField[b]) : a(c).jqGrid("hideCol", e.groupField[b]);
                        e.sortitems[b] = [];
                        e.sortnames[b] = [];
                        e.summaryval[b] = [];
                        if (e.groupSummary[b]) {
                            e.summary[b] = [];
                            for (var f = c.p.colModel, g = 0, h = f.length; g < h; g++) f[g].summaryType && e.summary[b].push({
                                nm: f[g].name,
                                st: f[g].summaryType,
                                v: ""
                            })
                        }
                    }
                    c.p.scroll = false;
                    c.p.rownumbers = false;
                    c.p.subGrid = false;
                    c.p.treeGrid = false;
                    c.p.gridview = true
                } else c.p.grouping = false;
                else c.p.grouping = false
            })
        },
        groupingPrepare: function (c, e, b, f) {
            this.each(function () {
                e[0] += "";
                var g = e[0].toString().split(" ").join(""),
                    h = this.p.groupingView,
                    j = this;
                if (b.hasOwnProperty(g)) b[g].push(c);
                else {
                    b[g] = [];
                    b[g].push(c);
                    h.sortitems[0].push(g);
                    h.sortnames[0].push(a.trim(e[0].toString()));
                    h.summaryval[0][g] = a.extend(true, [], h.summary[0])
                }
                h.groupSummary[0] && a.each(h.summaryval[0][g], function () {
                    this.v = a.isFunction(this.st) ? this.st.call(j, this.v, this.nm, f) : a(j).jqGrid("groupingCalculations." + this.st, this.v, this.nm, f)
                })
            });
            return b
        },
        groupingToggle: function (c) {
            this.each(function () {
                var e = this.p.groupingView,
                    b = c.lastIndexOf("_"),
                    f = c.substring(0, b + 1);
                b = parseInt(c.substring(b + 1), 10) + 1;
                var g = e.minusicon,
                    h = e.plusicon;
                if (a("#" + c + " span").hasClass(g)) {
                    e.showSummaryOnHide && e.groupSummary[0] ? a("#" + c).nextUntil(".jqfoot").hide() : a("#" + c).nextUntil("#" + f + String(b)).hide();
                    a("#" + c + " span").removeClass(g).addClass(h)
                } else {
                    a("#" + c).nextUntil("#" + f + String(b)).show();
                    a("#" + c + " span").removeClass(h).addClass(g)
                }
            });
            return false
        },
        groupingRender: function (c, e) {
            return this.each(function () {
                var b = this,
                    f = b.p.groupingView,
                    g = "",
                    h = "",
                    j, d = "",
                    l, k, m;
                if (!f.groupDataSorted) {
                    f.sortitems[0].sort();
                    f.sortnames[0].sort();
                    if (f.groupOrder[0].toLowerCase() == "desc") {
                        f.sortitems[0].reverse();
                        f.sortnames[0].reverse()
                    }
                }
                d = f.groupCollapse ? f.plusicon : f.minusicon;
                d += " tree-wrap-" + b.p.direction;
                for (m = 0; m < e;) {
                    if (b.p.colModel[m].name == f.groupField[0]) {
                        k = m;
                        break
                    }
                    m++
                }
                a.each(f.sortitems[0], function (q, p) {
                    j = b.p.id + "ghead_" + q;
                    h = "<span style='cursor:pointer;' class='ui-icon " + d + "' onclick=\"jQuery('#" + b.p.id + "').jqGrid('groupingToggle','" + j + "');return false;\"></span>";
                    try {
                        l = b.formatter(j, f.sortnames[0][q], k, f.sortitems[0])
                    } catch (u) {
                        l =
                        f.sortnames[0][q]
                    }
                    g += '<tr id="' + j + '" role="row" class= "ui-widget-content jqgroup ui-row-' + b.p.direction + '"><td colspan="' + e + '">' + h + a.jgrid.format(f.groupText[0], l, c[p].length) + "</td></tr>";
                    for (q = 0; q < c[p].length; q++) g += c[p][q].join("");
                    if (f.groupSummary[0]) {
                        q = "";
                        if (f.groupCollapse && !f.showSummaryOnHide) q = ' style="display:none;"';
                        g += "<tr" + q + ' role="row" class="ui-widget-content jqfoot ui-row-' + b.p.direction + '">';
                        q = f.summaryval[0][p];
                        for (var s = b.p.colModel, n, o = c[p].length, r = 0; r < e; r++) {
                            var C = "<td " + b.formatCol(r, 1, "") + ">&#160;</td>",
                                x = "{0}";
                            a.each(q, function () {
                                if (this.nm == s[r].name) {
                                    if (s[r].summaryTpl) x = s[r].summaryTpl;
                                    if (this.st == "avg") if (this.v && o > 0) this.v /= o;
                                    try {
                                        n = b.formatter("", this.v, r, this)
                                    } catch (B) {
                                        n = this.v
                                    }
                                    C = "<td " + b.formatCol(r, 1, "") + ">" + a.jgrid.format(x, n) + "</td>";
                                    return false
                                }
                            });
                            g += C
                        }
                        g += "</tr>"
                    }
                });
                a("#" + b.p.id + " tbody:first").append(g);
                g = null
            })
        },
        groupingGroupBy: function (c, e) {
            return this.each(function () {
                var b = this;
                if (typeof c == "string") c = [c];
                var f = b.p.groupingView;
                b.p.grouping = true;
                for (var g = 0; g < f.groupField.length; g++) f.groupColumnShow[g] || a(b).jqGrid("showCol", f.groupField[g]);
                b.p.groupingView = a.extend(b.p.groupingView, e || {});
                f.groupField = c;
                a(b).trigger("reloadGrid")
            })
        },
        groupingRemove: function (c) {
            return this.each(function () {
                var e = this;
                if (typeof c == "undefined") c = true;
                e.p.grouping = false;
                if (c === true) {
                    a("tr.jqgroup, tr.jqfoot", "#" + e.p.id + " tbody:first").remove();
                    a("tr.jqgrow:hidden", "#" + e.p.id + " tbody:first").show()
                } else a(e).trigger("reloadGrid")
            })
        },
        groupingCalculations: {
            sum: function (c, e, b) {
                return parseFloat(c || 0) + parseFloat(b[e] || 0)
            },
            min: function (c, e, b) {
                if (c === "") return parseFloat(b[e] || 0);
                return Math.min(parseFloat(c), parseFloat(b[e] || 0))
            },
            max: function (c, e, b) {
                if (c === "") return parseFloat(b[e] || 0);
                return Math.max(parseFloat(c), parseFloat(b[e] || 0))
            },
            count: function (c, e, b) {
                if (c === "") c = 0;
                return b.hasOwnProperty(e) ? c + 1 : 0
            },
            avg: function (c, e, b) {
                return parseFloat(c || 0) + parseFloat(b[e] || 0)
            }
        }
    })
})(jQuery);
(function (a) {
    a.jgrid.extend({
        jqGridImport: function (c) {
            c = a.extend({
                imptype: "xml",
                impstring: "",
                impurl: "",
                mtype: "GET",
                impData: {},
                xmlGrid: {
                    config: "roots>grid",
                    data: "roots>rows"
                },
                jsonGrid: {
                    config: "grid",
                    data: "data"
                },
                ajaxOptions: {}
            }, c || {});
            return this.each(function () {
                var e = this,
                    b = function (h, j) {
                        var d = a(j.xmlGrid.config, h)[0];
                        j = a(j.xmlGrid.data, h)[0];
                        var l;
                        if (xmlJsonClass.xml2json && a.jgrid.parse) {
                            d = xmlJsonClass.xml2json(d, " ");
                            d = a.jgrid.parse(d);
                            for (var k in d) if (d.hasOwnProperty(k)) l = d[k];
                            if (j) {
                                k = d.grid.datatype;
                                d.grid.datatype = "xmlstring";
                                d.grid.datastr = h;
                                a(e).jqGrid(l).jqGrid("setGridParam", {
                                    datatype: k
                                })
                            } else a(e).jqGrid(l)
                        } else alert("xml2json or parse are not present")
                    },
                    f = function (h, j) {
                        if (h && typeof h == "string") {
                            var d = a.jgrid.parse(h);
                            h = d[j.jsonGrid.config];
                            if (j = d[j.jsonGrid.data]) {
                                d = h.datatype;
                                h.datatype = "jsonstring";
                                h.datastr = j;
                                a(e).jqGrid(h).jqGrid("setGridParam", {
                                    datatype: d
                                })
                            } else a(e).jqGrid(h)
                        }
                    };
                switch (c.imptype) {
                case "xml":
                    a.ajax(a.extend({
                        url: c.impurl,
                        type: c.mtype,
                        data: c.impData,
                        dataType: "xml",
                        complete: function (h, j) {
                            if (j == "success") {
                                b(h.responseXML, c);
                                a.isFunction(c.importComplete) && c.importComplete(h)
                            }
                        }
                    }, c.ajaxOptions));
                    break;
                case "xmlstring":
                    if (c.impstring && typeof c.impstring == "string") {
                        var g = a.jgrid.stringToDoc(c.impstring);
                        if (g) {
                            b(g, c);
                            a.isFunction(c.importComplete) && c.importComplete(g);
                            c.impstring = null
                        }
                        g = null
                    }
                    break;
                case "json":
                    a.ajax(a.extend({
                        url: c.impurl,
                        type: c.mtype,
                        data: c.impData,
                        dataType: "json",
                        complete: function (h, j) {
                            if (j == "success") {
                                f(h.responseText, c);
                                a.isFunction(c.importComplete) && c.importComplete(h)
                            }
                        }
                    }, c.ajaxOptions));
                    break;
                case "jsonstring":
                    if (c.impstring && typeof c.impstring == "string") {
                        f(c.impstring, c);
                        a.isFunction(c.importComplete) && c.importComplete(c.impstring);
                        c.impstring = null
                    }
                    break
                }
            })
        },
        jqGridExport: function (c) {
            c = a.extend({
                exptype: "xmlstring",
                root: "grid",
                ident: "\t"
            }, c || {});
            var e = null;
            this.each(function () {
                if (this.grid) {
                    var b = a.extend({}, a(this).jqGrid("getGridParam"));
                    if (b.rownumbers) {
                        b.colNames.splice(0, 1);
                        b.colModel.splice(0, 1)
                    }
                    if (b.multiselect) {
                        b.colNames.splice(0, 1);
                        b.colModel.splice(0, 1)
                    }
                    if (b.subGrid) {
                        b.colNames.splice(0, 1);
                        b.colModel.splice(0, 1)
                    }
                    b.knv = null;
                    if (b.treeGrid) for (var f in b.treeReader) if (b.treeReader.hasOwnProperty(f)) {
                        b.colNames.splice(b.colNames.length - 1);
                        b.colModel.splice(b.colModel.length - 1)
                    }
                    switch (c.exptype) {
                    case "xmlstring":
                        e = "<" + c.root + ">" + xmlJsonClass.json2xml(b, c.ident) + "</" + c.root + ">";
                        break;
                    case "jsonstring":
                        e = "{" + xmlJsonClass.toJson(b, c.root, c.ident) + "}";
                        if (b.postData.filters !== undefined) {
                            e = e.replace(/filters":"/, 'filters":');
                            e = e.replace(/}]}"/, "}]}")
                        }
                        break
                    }
                }
            });
            return e
        },
        excelExport: function (c) {
            c = a.extend({
                exptype: "remote",
                url: null,
                oper: "oper",
                tag: "excel",
                exportOptions: {}
            }, c || {});
            return this.each(function () {
                if (this.grid) {
                    var e;
                    if (c.exptype == "remote") {
                        e = a.extend({}, this.p.postData);
                        e[c.oper] = c.tag;
                        e = jQuery.param(e);
                        e = c.url.indexOf("?") != -1 ? c.url + "&" + e : c.url + "?" + e;
                        window.location = e
                    }
                }
            })
        }
    })
})(jQuery);
(function (a) {
    a.jgrid.extend({
        editRow: function (c, e, b, f, g, h, j, d, l) {
            return this.each(function () {
                var k = this,
                    m, q, p = 0,
                    u = null,
                    s = {},
                    n, o;
                if (k.grid) {
                    n = a(k).jqGrid("getInd", c, true);
                    if (n !== false) if ((a(n).attr("editable") || "0") == "0" && !a(n).hasClass("not-editable-row")) {
                        o = k.p.colModel;
                        a("td", n).each(function (r) {
                            m = o[r].name;
                            var C = k.p.treeGrid === true && m == k.p.ExpandColumn;
                            if (C) q = a("span:first", this).html();
                            else
                            try {
                                q = a.unformat(this, {
                                    rowId: c,
                                    colModel: o[r]
                                }, r)
                            } catch (x) {
                                q = a(this).html()
                            }
                            if (m != "cb" && m != "subgrid" && m != "rn") {
                                if (k.p.autoencode) q = a.jgrid.htmlDecode(q);
                                s[m] = q;
                                if (o[r].editable === true) {
                                    if (u === null) u = r;
                                    C ? a("span:first", this).html("") : a(this).html("");
                                    var B = a.extend({}, o[r].editoptions || {}, {
                                        id: c + "_" + m,
                                        name: m
                                    });
                                    if (!o[r].edittype) o[r].edittype = "text";
                                    B = a.jgrid.createEl(o[r].edittype, B, q, true, a.extend({}, a.jgrid.ajaxOptions, k.p.ajaxSelectOptions || {}));
                                    a(B).addClass("editable");
                                    C ? a("span:first", this).append(B) : a(this).append(B);
                                    o[r].edittype == "select" && o[r].editoptions.multiple === true && a.browser.msie && a(B).width(a(B).width());
                                    p++
                                }
                            }
                        });
                        if (p > 0) {
                            s.id = c;
                            k.p.savedRow.push(s);
                            a(n).attr("editable", "1");
                            a("td:eq(" + u + ") input", n).focus();
                            e === true && a(n).bind("keydown", function (r) {
                                r.keyCode === 27 && a(k).jqGrid("restoreRow", c, l);
                                if (r.keyCode === 13) {
                                    if (r.target.tagName == "TEXTAREA") return true;
                                    a(k).jqGrid("saveRow", c, f, g, h, j, d, l);
                                    return false
                                }
                                r.stopPropagation()
                            });
                            a.isFunction(b) && b.call(k, c)
                        }
                    }
                }
            })
        },
        saveRow: function (c, e, b, f, g, h, j) {
            return this.each(function () {
                var d = this,
                    l, k = {},
                    m = {},
                    q, p, u, s;
                if (d.grid) {
                    s = a(d).jqGrid("getInd", c, true);
                    if (s !== false) {
                        q = a(s).attr("editable");
                        b = b ? b : d.p.editurl;
                        if (q === "1") {
                            var n;
                            a("td", s).each(function (x) {
                                n = d.p.colModel[x];
                                l = n.name;
                                if (l != "cb" && l != "subgrid" && n.editable === true && l != "rn") {
                                    switch (n.edittype) {
                                    case "checkbox":
                                        var B = ["Yes", "No"];
                                        if (n.editoptions) B = n.editoptions.value.split(":");
                                        k[l] = a("input", this).attr("checked") ? B[0] : B[1];
                                        break;
                                    case "text":
                                    case "password":
                                    case "textarea":
                                    case "button":
                                        k[l] = a("input, textarea", this).val();
                                        break;
                                    case "select":
                                        if (n.editoptions.multiple) {
                                            B = a("select", this);
                                            var D = [];
                                            k[l] = a(B).val();
                                            k[l] = k[l] ? k[l].join(",") : "";
                                            a("select > option:selected", this).each(function (y, A) {
                                                D[y] = a(A).text()
                                            });
                                            m[l] = D.join(",")
                                        } else {
                                            k[l] = a("select>option:selected", this).val();
                                            m[l] = a("select>option:selected", this).text()
                                        }
                                        if (n.formatter && n.formatter == "select") m = {};
                                        break;
                                    case "custom":
                                        try {
                                            if (n.editoptions && a.isFunction(n.editoptions.custom_value)) {
                                                k[l] = n.editoptions.custom_value.call(d, a(".customelement", this), "get");
                                                if (k[l] === undefined) throw "e2";
                                            } else
                                            throw "e1";
                                        } catch (z) {
                                            z == "e1" && a.jgrid.info_dialog(jQuery.jgrid.errors.errcap, "function 'custom_value' " + a.jgrid.edit.msg.nodefined, jQuery.jgrid.edit.bClose);
                                            z == "e2" ? a.jgrid.info_dialog(jQuery.jgrid.errors.errcap, "function 'custom_value' " + a.jgrid.edit.msg.novalue, jQuery.jgrid.edit.bClose) : a.jgrid.info_dialog(jQuery.jgrid.errors.errcap, z.message, jQuery.jgrid.edit.bClose)
                                        }
                                        break
                                    }
                                    u = a.jgrid.checkValues(k[l], x, d);
                                    if (u[0] === false) {
                                        u[1] = k[l] + " " + u[1];
                                        return false
                                    }
                                    if (d.p.autoencode) k[l] = a.jgrid.htmlEncode(k[l])
                                }
                            });
                            if (u[0] === false) try {
                                var o = a.jgrid.findPos(a("#" + a.jgrid.jqID(c), d.grid.bDiv)[0]);
                                a.jgrid.info_dialog(a.jgrid.errors.errcap, u[1], a.jgrid.edit.bClose, {
                                    left: o[0],
                                    top: o[1]
                                })
                            } catch (r) {
                                alert(u[1])
                            } else {
                                if (k) {
                                    var C;
                                    o = d.p.prmNames;
                                    C = o.oper;
                                    q = o.id;
                                    k[C] = o.editoper;
                                    k[q] = c;
                                    if (typeof d.p.inlineData == "undefined") d.p.inlineData = {};
                                    if (typeof f == "undefined") f = {};
                                    k = a.extend({}, k, d.p.inlineData, f)
                                }
                                if (b == "clientArray") {
                                    k = a.extend({}, k, m);
                                    d.p.autoencode && a.each(k, function (x, B) {
                                        k[x] = a.jgrid.htmlDecode(B)
                                    });
                                    q = a(d).jqGrid("setRowData", c, k);
                                    a(s).attr("editable", "0");
                                    for (o = 0; o < d.p.savedRow.length; o++) if (d.p.savedRow[o].id == c) {
                                        p = o;
                                        break
                                    }
                                    p >= 0 && d.p.savedRow.splice(p, 1);
                                    a.isFunction(g) && g.call(d, c, q)
                                } else {
                                    a("#lui_" + d.p.id).show();
                                    a.ajax(a.extend({
                                        url: b,
                                        data: a.isFunction(d.p.serializeRowData) ? d.p.serializeRowData.call(d, k) : k,
                                        type: "POST",
                                        complete: function (x, B) {
                                            a("#lui_" + d.p.id).hide();
                                            if (B === "success") if ((a.isFunction(e) ? e.call(d, x) : true) === true) {
                                                d.p.autoencode && a.each(k, function (D, z) {
                                                    k[D] = a.jgrid.htmlDecode(z)
                                                });
                                                k = a.extend({}, k, m);
                                                a(d).jqGrid("setRowData", c, k);
                                                a(s).attr("editable", "0");
                                                for (B = 0; B < d.p.savedRow.length; B++) if (d.p.savedRow[B].id == c) {
                                                    p = B;
                                                    break
                                                }
                                                p >= 0 && d.p.savedRow.splice(p, 1);
                                                a.isFunction(g) && g.call(d, c, x)
                                            } else {
                                                a.isFunction(h) && h.call(d, c, x, B);
                                                a(d).jqGrid("restoreRow", c, j)
                                            }
                                        },
                                        error: function (x, B) {
                                            a("#lui_" + d.p.id).hide();
                                            a.isFunction(h) ? h.call(d, c, x, B) : alert("Error Row: " + c + " Result: " + x.status + ":" + x.statusText + " Status: " + B);
                                            a(d).jqGrid("restoreRow", c, j)
                                        }
                                    }, a.jgrid.ajaxOptions, d.p.ajaxRowOptions || {}))
                                }
                                a(s).unbind("keydown")
                            }
                        }
                    }
                }
            })
        },
        restoreRow: function (c, e) {
            return this.each(function () {
                var b = this,
                    f, g, h = {};
                if (b.grid) {
                    g = a(b).jqGrid("getInd", c, true);
                    if (g !== false) {
                        for (var j = 0; j < b.p.savedRow.length; j++) if (b.p.savedRow[j].id == c) {
                            f = j;
                            break
                        }
                        if (f >= 0) {
                            if (a.isFunction(a.fn.datepicker)) try {
                                a("input.hasDatepicker", "#" + a.jgrid.jqID(g.id)).datepicker("hide")
                            } catch (d) {}
                            a.each(b.p.colModel, function () {
                                if (this.editable === true && this.name in b.p.savedRow[f]) h[this.name] = b.p.savedRow[f][this.name]
                            });
                            a(b).jqGrid("setRowData", c, h);
                            a(g).attr("editable", "0").unbind("keydown");
                            b.p.savedRow.splice(f, 1)
                        }
                        a.isFunction(e) && e.call(b, c)
                    }
                }
            })
        }
    })
})(jQuery);
(function (a) {
    if (a.browser.msie && a.browser.version == 8) a.expr[":"].hidden = function (e) {
        return e.offsetWidth === 0 || e.offsetHeight === 0 || e.style.display == "none"
    };
    a.jgrid._multiselect = false;
    if (a.ui) if (a.ui.multiselect) {
        if (a.ui.multiselect.prototype._setSelected) {
            var c = a.ui.multiselect.prototype._setSelected;
            a.ui.multiselect.prototype._setSelected = function (e, b) {
                e = c.call(this, e, b);
                if (b && this.selectedList) {
                    var f = this.element;
                    this.selectedList.find("li").each(function () {
                        a(this).data("optionLink") && a(this).data("optionLink").remove().appendTo(f)
                    })
                }
                return e
            }
        }
        if (a.ui.multiselect.prototype.destroy) a.ui.multiselect.prototype.destroy =

        function () {
            this.element.show();
            this.container.remove();
            a.Widget === undefined ? a.widget.prototype.destroy.apply(this, arguments) : a.Widget.prototype.destroy.apply(this, arguments)
        };
        a.jgrid._multiselect = true
    }
    a.jgrid.extend({
        sortableColumns: function (e) {
            return this.each(function () {
                function b() {
                    f.p.disableClick = true
                }
                var f = this,
                    g = {
                        tolerance: "pointer",
                        axis: "x",
                        scrollSensitivity: "1",
                        items: ">th:not(:has(#jqgh_cb,#jqgh_rn,#jqgh_subgrid),:hidden)",
                        placeholder: {
                            element: function (j) {
                                return a(document.createElement(j[0].nodeName)).addClass(j[0].className + " ui-sortable-placeholder ui-state-highlight").removeClass("ui-sortable-helper")[0]
                            },
                            update: function (j, d) {
                                d.height(j.currentItem.innerHeight() - parseInt(j.currentItem.css("paddingTop") || 0, 10) - parseInt(j.currentItem.css("paddingBottom") || 0, 10));
                                d.width(j.currentItem.innerWidth() - parseInt(j.currentItem.css("paddingLeft") || 0, 10) - parseInt(j.currentItem.css("paddingRight") || 0, 10))
                            }
                        },
                        update: function (j, d) {
                            j = a(d.item).parent();
                            j = a(">th", j);
                            var l = {};
                            a.each(f.p.colModel, function (m) {
                                l[this.name] = m
                            });
                            var k = [];
                            j.each(function () {
                                var m = a(">div", this).get(0).id.replace(/^jqgh_/, "");
                                m in l && k.push(l[m])
                            });
                            a(f).jqGrid("remapColumns", k, true, true);
                            a.isFunction(f.p.sortable.update) && f.p.sortable.update(k);
                            setTimeout(function () {
                                f.p.disableClick = false
                            }, 50)
                        }
                    };
                if (f.p.sortable.options) a.extend(g, f.p.sortable.options);
                else if (a.isFunction(f.p.sortable)) f.p.sortable = {
                    update: f.p.sortable
                };
                if (g.start) {
                    var h = g.start;
                    g.start = function (j, d) {
                        b();
                        h.call(this, j, d)
                    }
                } else g.start = b;
                if (f.p.sortable.exclude) g.items += ":not(" + f.p.sortable.exclude + ")";
                e.sortable(g).data("sortable").floating = true
            })
        },
        columnChooser: function (e) {
            function b(p, u, s) {
                if (u >= 0) {
                    var n = p.slice(),
                        o = n.splice(u, Math.max(p.length - u, u));
                    if (u > p.length) u = p.length;
                    n[u] = s;
                    return n.concat(o)
                }
            }
            function f(p, u) {
                if (p) if (typeof p == "string") a.fn[p] && a.fn[p].apply(u, a.makeArray(arguments).slice(2));
                else a.isFunction(p) && p.apply(u, a.makeArray(arguments).slice(2))
            }
            var g = this;
            if (!a("#colchooser_" + g[0].p.id).length) {
                var h = a('<div id="colchooser_' + g[0].p.id + '" style="position:relative;overflow:hidden"><div><select multiple="multiple"></select></div></div>'),
                    j = a("select", h);
                e = a.extend({
                    width: 420,
                    height: 240,
                    classname: null,
                    done: function (p) {
                        p && g.jqGrid("remapColumns", p, true)
                    },
                    msel: "multiselect",
                    dlog: "dialog",
                    dlog_opts: function (p) {
                        var u = {};
                        u[p.bSubmit] = function () {
                            p.apply_perm();
                            p.cleanup(false)
                        };
                        u[p.bCancel] = function () {
                            p.cleanup(true)
                        };
                        return {
                            buttons: u,
                            close: function () {
                                p.cleanup(true)
                            },
                            modal: false,
                            resizable: false,
                            width: p.width + 20
                        }
                    },
                    apply_perm: function () {
                        a("option", j).each(function () {
                            this.selected ? g.jqGrid("showCol", d[this.value].name) : g.jqGrid("hideCol", d[this.value].name)
                        });
                        var p = [];
                        a("option[selected]", j).each(function () {
                            p.push(parseInt(this.value, 10))
                        });
                        a.each(p, function () {
                            delete k[d[parseInt(this, 10)].name]
                        });
                        a.each(k, function () {
                            var u = parseInt(this, 10);
                            p = b(p, u, u)
                        });
                        e.done && e.done.call(g, p)
                    },
                    cleanup: function (p) {
                        f(e.dlog, h, "destroy");
                        f(e.msel, j, "destroy");
                        h.remove();
                        p && e.done && e.done.call(g)
                    },
                    msel_opts: {}
                }, a.jgrid.col, e || {});
                if (a.ui) if (a.ui.multiselect) if (e.msel == "multiselect") {
                    if (!a.jgrid._multiselect) {
                        alert("Multiselect plugin loaded after jqGrid. Please load the plugin before the jqGrid!");
                        return
                    }
                    e.msel_opts = a.extend(a.ui.multiselect.defaults, e.msel_opts)
                }
                e.caption && h.attr("title", e.caption);
                if (e.classname) {
                    h.addClass(e.classname);
                    j.addClass(e.classname)
                }
                if (e.width) {
                    a(">div", h).css({
                        width: e.width,
                        margin: "0 auto"
                    });
                    j.css("width", e.width)
                }
                if (e.height) {
                    a(">div", h).css("height", e.height);
                    j.css("height", e.height - 10)
                }
                var d = g.jqGrid("getGridParam", "colModel"),
                    l = g.jqGrid("getGridParam", "colNames"),
                    k = {},
                    m = [];
                j.empty();
                a.each(d, function (p) {
                    k[this.name] = p;
                    if (this.hidedlg) this.hidden || m.push(p);
                    else j.append("<option value='" + p + "' " + (this.hidden ? "" : "selected='selected'") + ">" + l[p] + "</option>")
                });
                var q = a.isFunction(e.dlog_opts) ? e.dlog_opts.call(g, e) : e.dlog_opts;
                f(e.dlog, h, q);
                q = a.isFunction(e.msel_opts) ? e.msel_opts.call(g, e) : e.msel_opts;
                f(e.msel, j, q)
            }
        },
        sortableRows: function (e) {
            return this.each(function () {
                var b = this;
                if (b.grid) if (!b.p.treeGrid) if (a.fn.sortable) {
                    e = a.extend({
                        cursor: "move",
                        axis: "y",
                        items: ".jqgrow"
                    }, e || {});
                    if (e.start && a.isFunction(e.start)) {
                        e._start_ = e.start;
                        delete e.start
                    } else e._start_ =
                    false;
                    if (e.update && a.isFunction(e.update)) {
                        e._update_ = e.update;
                        delete e.update
                    } else e._update_ = false;
                    e.start = function (f, g) {
                        a(g.item).css("border-width", "0px");
                        a("td", g.item).each(function (d) {
                            this.style.width = b.grid.cols[d].style.width
                        });
                        if (b.p.subGrid) {
                            var h = a(g.item).attr("id");
                            try {
                                a(b).jqGrid("collapseSubGridRow", h)
                            } catch (j) {}
                        }
                        e._start_ && e._start_.apply(this, [f, g])
                    };
                    e.update = function (f, g) {
                        a(g.item).css("border-width", "");
                        b.p.rownumbers === true && a("td.jqgrid-rownum", b.rows).each(function (h) {
                            a(this).html(h + 1)
                        });
                        e._update_ && e._update_.apply(this, [f, g])
                    };
                    a("tbody:first", b).sortable(e);
                    a("tbody:first", b).disableSelection()
                }
            })
        },
        gridDnD: function (e) {
            return this.each(function () {
                function b() {
                    var h = a.data(f, "dnd");
                    a("tr.jqgrow:not(.ui-draggable)", f).draggable(a.isFunction(h.drag) ? h.drag.call(a(f), h) : h.drag)
                }
                var f = this;
                if (f.grid) if (!f.p.treeGrid) if (a.fn.draggable && a.fn.droppable) {
                    a("#jqgrid_dnd").html() === null && a("body").append("<table id='jqgrid_dnd' class='ui-jqgrid-dnd'></table>");
                    if (typeof e == "string" && e == "updateDnD" && f.p.jqgdnd === true) b();
                    else {
                        e = a.extend({
                            drag: function (h) {
                                return a.extend({
                                    start: function (j, d) {
                                        if (f.p.subGrid) {
                                            var l = a(d.helper).attr("id");
                                            try {
                                                a(f).jqGrid("collapseSubGridRow", l)
                                            } catch (k) {}
                                        }
                                        for (l = 0; l < a.data(f, "dnd").connectWith.length; l++) a(a.data(f, "dnd").connectWith[l]).jqGrid("getGridParam", "reccount") == "0" && a(a.data(f, "dnd").connectWith[l]).jqGrid("addRowData", "jqg_empty_row", {});
                                        d.helper.addClass("ui-state-highlight");
                                        a("td", d.helper).each(function (m) {
                                            this.style.width = f.grid.headers[m].width + "px"
                                        });
                                        h.onstart && a.isFunction(h.onstart) && h.onstart.call(a(f), j, d)
                                    },
                                    stop: function (j, d) {
                                        if (d.helper.dropped) {
                                            var l = a(d.helper).attr("id");
                                            a(f).jqGrid("delRowData", l)
                                        }
                                        for (l = 0; l < a.data(f, "dnd").connectWith.length; l++) a(a.data(f, "dnd").connectWith[l]).jqGrid("delRowData", "jqg_empty_row");
                                        h.onstop && a.isFunction(h.onstop) && h.onstop.call(a(f), j, d)
                                    }
                                }, h.drag_opts || {})
                            },
                            drop: function (h) {
                                return a.extend({
                                    accept: function (j) {
                                        if (!a(j).hasClass("jqgrow")) return j;
                                        var d = a(j).closest("table.ui-jqgrid-btable");
                                        if (d.length > 0 && a.data(d[0], "dnd") !== undefined) {
                                            j = a.data(d[0], "dnd").connectWith;
                                            return a.inArray("#" + this.id, j) != -1 ? true : false
                                        }
                                        return j
                                    },
                                    drop: function (j, d) {
                                        if (a(d.draggable).hasClass("jqgrow")) {
                                            var l = a(d.draggable).attr("id");
                                            l = d.draggable.parent().parent().jqGrid("getRowData", l);
                                            if (!h.dropbyname) {
                                                var k = 0,
                                                    m = {},
                                                    q, p = a("#" + this.id).jqGrid("getGridParam", "colModel");
                                                try {
                                                    for (var u in l) {
                                                        if (l.hasOwnProperty(u) && p[k]) {
                                                            q = p[k].name;
                                                            m[q] = l[u]
                                                        }
                                                        k++
                                                    }
                                                    l = m
                                                } catch (s) {}
                                            }
                                            d.helper.dropped = true;
                                            if (h.beforedrop && a.isFunction(h.beforedrop)) {
                                                q =
                                                h.beforedrop.call(this, j, d, l, a("#" + f.id), a(this));
                                                if (typeof q != "undefined" && q !== null && typeof q == "object") l = q
                                            }
                                            if (d.helper.dropped) {
                                                var n;
                                                if (h.autoid) if (a.isFunction(h.autoid)) n = h.autoid.call(this, l);
                                                else {
                                                    n = Math.ceil(Math.random() * 1E3);
                                                    n = h.autoidprefix + n
                                                }
                                                a("#" + this.id).jqGrid("addRowData", n, l, h.droppos)
                                            }
                                            h.ondrop && a.isFunction(h.ondrop) && h.ondrop.call(this, j, d, l)
                                        }
                                    }
                                }, h.drop_opts || {})
                            },
                            onstart: null,
                            onstop: null,
                            beforedrop: null,
                            ondrop: null,
                            drop_opts: {
                                activeClass: "ui-state-active",
                                hoverClass: "ui-state-hover"
                            },
                            drag_opts: {
                                revert: "invalid",
                                helper: "clone",
                                cursor: "move",
                                appendTo: "#jqgrid_dnd",
                                zIndex: 5E3
                            },
                            dropbyname: false,
                            droppos: "first",
                            autoid: true,
                            autoidprefix: "dnd_"
                        }, e || {});
                        if (e.connectWith) {
                            e.connectWith = e.connectWith.split(",");
                            e.connectWith = a.map(e.connectWith, function (h) {
                                return a.trim(h)
                            });
                            a.data(f, "dnd", e);
                            f.p.reccount != "0" && !f.p.jqgdnd && b();
                            f.p.jqgdnd = true;
                            for (var g = 0; g < e.connectWith.length; g++) a(e.connectWith[g]).droppable(a.isFunction(e.drop) ? e.drop.call(a(f), e) : e.drop)
                        }
                    }
                }
            })
        },
        gridResize: function (e) {
            return this.each(function () {
                var b =
                this;
                if (b.grid && a.fn.resizable) {
                    e = a.extend({}, e || {});
                    if (e.alsoResize) {
                        e._alsoResize_ = e.alsoResize;
                        delete e.alsoResize
                    } else e._alsoResize_ = false;
                    if (e.stop && a.isFunction(e.stop)) {
                        e._stop_ = e.stop;
                        delete e.stop
                    } else e._stop_ = false;
                    e.stop = function (f, g) {
                        a(b).jqGrid("setGridParam", {
                            height: a("#gview_" + b.p.id + " .ui-jqgrid-bdiv").height()
                        });
                        a(b).jqGrid("setGridWidth", g.size.width, e.shrinkToFit);
                        e._stop_ && e._stop_.call(b, f, g)
                    };
                    e.alsoResize = e._alsoResize_ ? eval("(" + ("{'#gview_" + b.p.id + " .ui-jqgrid-bdiv':true,'" + e._alsoResize_ + "':true}") + ")") : a(".ui-jqgrid-bdiv", "#gview_" + b.p.id);
                    delete e._alsoResize_;
                    a("#gbox_" + b.p.id).resizable(e)
                }
            })
        }
    })
})(jQuery);
(function (a) {
    a.jgrid.extend({
        getPostData: function () {
            var c = this[0];
            if (c.grid) return c.p.postData
        },
        setPostData: function (c) {
            var e = this[0];
            if (e.grid) if (typeof c === "object") e.p.postData = c;
            else alert("Error: cannot add a non-object postData value. postData unchanged.")
        },
        appendPostData: function (c) {
            var e = this[0];
            if (e.grid) typeof c === "object" ? a.extend(e.p.postData, c) : alert("Error: cannot append a non-object postData value. postData unchanged.")
        },
        setPostDataItem: function (c, e) {
            var b = this[0];
            if (b.grid) b.p.postData[c] =
            e
        },
        getPostDataItem: function (c) {
            var e = this[0];
            if (e.grid) return e.p.postData[c]
        },
        removePostDataItem: function (c) {
            var e = this[0];
            e.grid && delete e.p.postData[c]
        },
        getUserData: function () {
            var c = this[0];
            if (c.grid) return c.p.userData
        },
        getUserDataItem: function (c) {
            var e = this[0];
            if (e.grid) return e.p.userData[c]
        }
    })
})(jQuery);
(function (a) {
    a.jgrid.extend({
        setColumns: function (c) {
            c = a.extend({
                top: 0,
                left: 0,
                width: 200,
                height: "auto",
                dataheight: "auto",
                modal: false,
                drag: true,
                beforeShowForm: null,
                afterShowForm: null,
                afterSubmitForm: null,
                closeOnEscape: true,
                ShrinkToFit: false,
                jqModal: false,
                saveicon: [true, "left", "ui-icon-disk"],
                closeicon: [true, "left", "ui-icon-close"],
                onClose: null,
                colnameview: true,
                closeAfterSubmit: true,
                updateAfterCheck: false,
                recreateForm: false
            }, a.jgrid.col, c || {});
            return this.each(function () {
                var e = this;
                if (e.grid) {
                    var b = typeof c.beforeShowForm === "function" ? true : false,
                        f = typeof c.afterShowForm === "function" ? true : false,
                        g = typeof c.afterSubmitForm === "function" ? true : false,
                        h = e.p.id,
                        j = "ColTbl_" + h,
                        d = {
                            themodal: "colmod" + h,
                            modalhead: "colhd" + h,
                            modalcontent: "colcnt" + h,
                            scrollelm: j
                        };
                    c.recreateForm === true && a("#" + d.themodal).html() != null && a("#" + d.themodal).remove();
                    if (a("#" + d.themodal).html() != null) {
                        b && c.beforeShowForm(a("#" + j));
                        a.jgrid.viewModal("#" + d.themodal, {
                            gbox: "#gbox_" + h,
                            jqm: c.jqModal,
                            jqM: false,
                            modal: c.modal
                        })
                    } else {
                        var l = isNaN(c.dataheight) ? c.dataheight : c.dataheight + "px";
                        l = "<div id='" + j + "' class='formdata' style='width:100%;overflow:auto;position:relative;height:" + l + ";'>";
                        l += "<table class='ColTable' cellspacing='1' cellpading='2' border='0'><tbody>";
                        for (i = 0; i < this.p.colNames.length; i++) e.p.colModel[i].hidedlg || (l += "<tr><td style='white-space: pre;'><input type='checkbox' style='margin-right:5px;' id='col_" + this.p.colModel[i].name + "' class='cbox' value='T' " + (this.p.colModel[i].hidden === false ? "checked" : "") + "/><label for='col_" + this.p.colModel[i].name + "'>" + this.p.colNames[i] + (c.colnameview ? " (" + this.p.colModel[i].name + ")" : "") + "</label></td></tr>");
                        l += "</tbody></table></div>";
                        l += "<table border='0' class='EditTable' id='" + j + "_2'><tbody><tr style='display:block;height:3px;'><td></td></tr><tr><td class='DataTD ui-widget-content'></td></tr><tr><td class='ColButton EditButton'>" + (!c.updateAfterCheck ? "<a href='javascript:void(0)' id='dData' class='fm-button ui-state-default ui-corner-all'>" + c.bSubmit + "</a>" : "") + "&#160;" + ("<a href='javascript:void(0)' id='eData' class='fm-button ui-state-default ui-corner-all'>" + c.bCancel + "</a>") + "</td></tr></tbody></table>";
                        c.gbox = "#gbox_" + h;
                        a.jgrid.createModal(d, l, c, "#gview_" + e.p.id, a("#gview_" + e.p.id)[0]);
                        if (c.saveicon[0] == true) a("#dData", "#" + j + "_2").addClass(c.saveicon[1] == "right" ? "fm-button-icon-right" : "fm-button-icon-left").append("<span class='ui-icon " + c.saveicon[2] + "'></span>");
                        if (c.closeicon[0] == true) a("#eData", "#" + j + "_2").addClass(c.closeicon[1] == "right" ? "fm-button-icon-right" : "fm-button-icon-left").append("<span class='ui-icon " + c.closeicon[2] + "'></span>");
                        c.updateAfterCheck ? a(":input", "#" + j).click(function () {
                            var k = this.id.substr(4);
                            if (k) {
                                this.checked ? a(e).jqGrid("showCol", k) : a(e).jqGrid("hideCol", k);
                                c.ShrinkToFit === true && a(e).jqGrid("setGridWidth", e.grid.width - 0.001, true)
                            }
                            return this
                        }) : a("#dData", "#" + j + "_2").click(function () {
                            for (i = 0; i < e.p.colModel.length; i++) if (!e.p.colModel[i].hidedlg) {
                                var k = e.p.colModel[i].name.replace(/\./g, "\\.");
                                if (a("#col_" + k, "#" + j).attr("checked")) {
                                    a(e).jqGrid("showCol", e.p.colModel[i].name);
                                    a("#col_" + k, "#" + j).attr("defaultChecked", true)
                                } else {
                                    a(e).jqGrid("hideCol", e.p.colModel[i].name);
                                    a("#col_" + k, "#" + j).attr("defaultChecked", "")
                                }
                            }
                            c.ShrinkToFit === true && a(e).jqGrid("setGridWidth", e.grid.width - 0.001, true);
                            c.closeAfterSubmit && a.jgrid.hideModal("#" + d.themodal, {
                                gb: "#gbox_" + h,
                                jqm: c.jqModal,
                                onClose: c.onClose
                            });
                            g && c.afterSubmitForm(a("#" + j));
                            return false
                        });
                        a("#eData", "#" + j + "_2").click(function () {
                            a.jgrid.hideModal("#" + d.themodal, {
                                gb: "#gbox_" + h,
                                jqm: c.jqModal,
                                onClose: c.onClose
                            });
                            return false
                        });
                        a("#dData, #eData", "#" + j + "_2").hover(function () {
                            a(this).addClass("ui-state-hover")
                        }, function () {
                            a(this).removeClass("ui-state-hover")
                        });
                        b && c.beforeShowForm(a("#" + j));
                        a.jgrid.viewModal("#" + d.themodal, {
                            gbox: "#gbox_" + h,
                            jqm: c.jqModal,
                            jqM: true,
                            modal: c.modal
                        })
                    }
                    f && c.afterShowForm(a("#" + j))
                }
            })
        }
    })
})(jQuery);
(function (a) {
    a.jgrid.extend({
        setSubGrid: function () {
            return this.each(function () {
                var c = this;
                c.p.colNames.unshift("");
                c.p.colModel.unshift({
                    name: "subgrid",
                    width: a.browser.safari ? c.p.subGridWidth + c.p.cellLayout : c.p.subGridWidth,
                    sortable: false,
                    resizable: false,
                    hidedlg: true,
                    search: false,
                    fixed: true
                });
                c = c.p.subGridModel;
                if (c[0]) {
                    c[0].align = a.extend([], c[0].align || []);
                    for (var e = 0; e < c[0].name.length; e++) c[0].align[e] = c[0].align[e] || "left"
                }
            })
        },
        addSubGridCell: function (c, e) {
            var b = "",
                f, g;
            this.each(function () {
                b =
                this.formatCol(c, e);
                f = this.p.gridview;
                g = this.p.id
            });
            return f === false ? '<td role="grid" aria-describedby="' + g + '_subgrid" class="ui-sgcollapsed sgcollapsed" ' + b + "><a href='javascript:void(0);'><span class='ui-icon ui-icon-plus'></span></a></td>" : '<td role="grid" aria-describedby="' + g + '_subgrid" ' + b + "></td>"
        },
        addSubGrid: function (c, e) {
            return this.each(function () {
                var b = this;
                if (b.grid) {
                    var f = function (s, n, o) {
                        n = a("<td align='" + b.p.subGridModel[0].align[o] + "'></td>").html(n);
                        a(s).append(n)
                    },
                        g = function (s, n) {
                            var o, r, C, x = a("<table cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>"),
                                B = a("<tr></tr>");
                            for (r = 0; r < b.p.subGridModel[0].name.length; r++) {
                                o = a("<th class='ui-state-default ui-th-subgrid ui-th-column ui-th-" + b.p.direction + "'></th>");
                                a(o).html(b.p.subGridModel[0].name[r]);
                                a(o).width(b.p.subGridModel[0].width[r]);
                                a(B).append(o)
                            }
                            a(x).append(B);
                            if (s) {
                                C = b.p.xmlReader.subgrid;
                                a(C.root + " " + C.row, s).each(function () {
                                    B = a("<tr class='ui-widget-content ui-subtblcell'></tr>");
                                    if (C.repeatitems === true) a(C.cell, this).each(function (z) {
                                        f(B, a(this).text() || "&#160;", z)
                                    });
                                    else {
                                        var D = b.p.subGridModel[0].mapping || b.p.subGridModel[0].name;
                                        if (D) for (r = 0; r < D.length; r++) f(B, a(D[r], this).text() || "&#160;", r)
                                    }
                                    a(x).append(B)
                                })
                            }
                            s = a("table:first", b.grid.bDiv).attr("id") + "_";
                            a("#" + s + n).append(x);
                            b.grid.hDiv.loading = false;
                            a("#load_" + b.p.id).hide();
                            return false
                        },
                        h = function (s, n) {
                            var o, r, C, x, B = a("<table cellspacing='0' cellpadding='0' border='0'><tbody></tbody></table>"),
                                D = a("<tr></tr>");
                            for (r = 0; r < b.p.subGridModel[0].name.length; r++) {
                                o =
                                a("<th class='ui-state-default ui-th-subgrid ui-th-column ui-th-" + b.p.direction + "'></th>");
                                a(o).html(b.p.subGridModel[0].name[r]);
                                a(o).width(b.p.subGridModel[0].width[r]);
                                a(D).append(o)
                            }
                            a(B).append(D);
                            if (s) {
                                o = b.p.jsonReader.subgrid;
                                s = s[o.root];
                                if (typeof s !== "undefined") for (r = 0; r < s.length; r++) {
                                    C = s[r];
                                    D = a("<tr class='ui-widget-content ui-subtblcell'></tr>");
                                    if (o.repeatitems === true) {
                                        if (o.cell) C = C[o.cell];
                                        for (x = 0; x < C.length; x++) f(D, C[x] || "&#160;", x)
                                    } else {
                                        var z = b.p.subGridModel[0].mapping || b.p.subGridModel[0].name;
                                        if (z.length) for (x = 0; x < z.length; x++) f(D, C[z[x]] || "&#160;", x)
                                    }
                                    a(B).append(D)
                                }
                            }
                            r = a("table:first", b.grid.bDiv).attr("id") + "_";
                            a("#" + r + n).append(B);
                            b.grid.hDiv.loading = false;
                            a("#load_" + b.p.id).hide();
                            return false
                        },
                        j = function (s) {
                            var n, o, r, C;
                            n = a(s).attr("id");
                            o = {
                                nd_: (new Date).getTime()
                            };
                            o[b.p.prmNames.subgridid] = n;
                            if (!b.p.subGridModel[0]) return false;
                            if (b.p.subGridModel[0].params) for (C = 0; C < b.p.subGridModel[0].params.length; C++) for (r = 0; r < b.p.colModel.length; r++) if (b.p.colModel[r].name == b.p.subGridModel[0].params[C]) o[b.p.colModel[r].name] =
                            a("td:eq(" + r + ")", s).text().replace(/\&#160\;/ig, "");
                            if (!b.grid.hDiv.loading) {
                                b.grid.hDiv.loading = true;
                                a("#load_" + b.p.id).show();
                                if (!b.p.subgridtype) b.p.subgridtype = b.p.datatype;
                                if (a.isFunction(b.p.subgridtype)) b.p.subgridtype.call(b, o);
                                else b.p.subgridtype = b.p.subgridtype.toLowerCase();
                                switch (b.p.subgridtype) {
                                case "xml":
                                case "json":
                                    a.ajax(a.extend({
                                        type: b.p.mtype,
                                        url: b.p.subGridUrl,
                                        dataType: b.p.subgridtype,
                                        data: a.isFunction(b.p.serializeSubGridData) ? b.p.serializeSubGridData.call(b, o) : o,
                                        complete: function (x) {
                                            b.p.subgridtype == "xml" ? g(x.responseXML, n) : h(a.jgrid.parse(x.responseText), n)
                                        }
                                    }, a.jgrid.ajaxOptions, b.p.ajaxSubgridOptions || {}));
                                    break
                                }
                            }
                            return false
                        },
                        d, l, k, m, q, p, u;
                    a("td:eq(" + e + ")", c).click(function () {
                        if (a(this).hasClass("sgcollapsed")) {
                            k = b.p.id;
                            d = a(this).parent();
                            m = e >= 1 ? "<td colspan='" + e + "'>&#160;</td>" : "";
                            l = a(d).attr("id");
                            u = true;
                            if (a.isFunction(b.p.subGridBeforeExpand)) u = b.p.subGridBeforeExpand.call(b, k + "_" + l, l);
                            if (u === false) return false;
                            q = 0;
                            a.each(b.p.colModel, function () {
                                if (this.hidden === true || this.name == "rn" || this.name == "cb") q++
                            });
                            p = "<tr role='row' class='ui-subgrid'>" + m + "<td class='ui-widget-content subgrid-cell'><span class='ui-icon ui-icon-carat-1-sw'/></td><td colspan='" + parseInt(b.p.colNames.length - 1 - q, 10) + "' class='ui-widget-content subgrid-data'><div id=" + k + "_" + l + " class='tablediv'>";
                            a(this).parent().after(p + "</div></td></tr>");
                            a.isFunction(b.p.subGridRowExpanded) ? b.p.subGridRowExpanded.call(b, k + "_" + l, l) : j(d);
                            a(this).html("<a href='javascript:void(0);'><span class='ui-icon ui-icon-minus'></span></a>").removeClass("sgcollapsed").addClass("sgexpanded")
                        } else if (a(this).hasClass("sgexpanded")) {
                            u =
                            true;
                            if (a.isFunction(b.p.subGridRowColapsed)) {
                                d = a(this).parent();
                                l = a(d).attr("id");
                                u = b.p.subGridRowColapsed.call(b, k + "_" + l, l)
                            }
                            if (u === false) return false;
                            a(this).parent().next().remove(".ui-subgrid");
                            a(this).html("<a href='javascript:void(0);'><span class='ui-icon ui-icon-plus'></span></a>").removeClass("sgexpanded").addClass("sgcollapsed")
                        }
                        return false
                    });
                    b.subGridXml = function (s, n) {
                        g(s, n)
                    };
                    b.subGridJson = function (s, n) {
                        h(s, n)
                    }
                }
            })
        },
        expandSubGridRow: function (c) {
            return this.each(function () {
                var e = this;
                if (e.grid || c) if (e.p.subGrid === true) if (e = a(this).jqGrid("getInd", c, true))(e = a("td.sgcollapsed", e)[0]) && a(e).trigger("click")
            })
        },
        collapseSubGridRow: function (c) {
            return this.each(function () {
                var e = this;
                if (e.grid || c) if (e.p.subGrid === true) if (e = a(this).jqGrid("getInd", c, true))(e = a("td.sgexpanded", e)[0]) && a(e).trigger("click")
            })
        },
        toggleSubGridRow: function (c) {
            return this.each(function () {
                var e = this;
                if (e.grid || c) if (e.p.subGrid === true) if (e = a(this).jqGrid("getInd", c, true)) {
                    var b = a("td.sgcollapsed", e)[0];
                    if (b) a(b).trigger("click");
                    else(b = a("td.sgexpanded", e)[0]) && a(b).trigger("click")
                }
            })
        }
    })
})(jQuery);

function tableToGrid(a, c) {
    jQuery(a).each(function () {
        if (!this.grid) {
            jQuery(this).width("99%");
            var e = jQuery(this).width(),
                b = jQuery("input[type=checkbox]:first", jQuery(this)),
                f = jQuery("input[type=radio]:first", jQuery(this));
            b = b.length > 0;
            f = !b && f.length > 0;
            var g = b || f,
                h = [],
                j = [];
            jQuery("th", jQuery(this)).each(function () {
                if (h.length === 0 && g) {
                    h.push({
                        name: "__selection__",
                        index: "__selection__",
                        width: 0,
                        hidden: true
                    });
                    j.push("__selection__")
                } else {
                    h.push({
                        name: jQuery(this).attr("id") || jQuery.trim(jQuery.jgrid.stripHtml(jQuery(this).html())).split(" ").join("_"),
                        index: jQuery(this).attr("id") || jQuery.trim(jQuery.jgrid.stripHtml(jQuery(this).html())).split(" ").join("_"),
                        width: jQuery(this).width() || 150
                    });
                    j.push(jQuery(this).html())
                }
            });
            var d = [],
                l = [],
                k = [];
            jQuery("tbody > tr", jQuery(this)).each(function () {
                var m = {},
                    q = 0;
                jQuery("td", jQuery(this)).each(function () {
                    if (q === 0 && g) {
                        var p = jQuery("input", jQuery(this)),
                            u = p.attr("value");
                        l.push(u || d.length);
                        p.attr("checked") && k.push(u);
                        m[h[q].name] = p.attr("value")
                    } else m[h[q].name] = jQuery(this).html();
                    q++
                });
                q > 0 && d.push(m)
            });
            jQuery(this).empty();
            jQuery(this).addClass("scroll");
            jQuery(this).jqGrid(jQuery.extend({
                datatype: "local",
                width: e,
                colNames: j,
                colModel: h,
                multiselect: b
            }, c || {}));
            for (e = 0; e < d.length; e++) {
                f = null;
                if (l.length > 0) if ((f = l[e]) && f.replace) f = encodeURIComponent(f).replace(/[.\-%]/g, "_");
                if (f === null) f = e + 1;
                jQuery(this).jqGrid("addRowData", f, d[e])
            }
            for (e = 0; e < k.length; e++) jQuery(this).jqGrid("setSelection", k[e])
        }
    })
}(function (a) {
    a.jgrid.extend({
        setTreeNode: function (c, e) {
            return this.each(function () {
                var b = this;
                if (b.grid && b.p.treeGrid) {
                    var f = b.p.expColInd,
                        g = b.p.treeReader.expanded_field,
                        h = b.p.treeReader.leaf_field,
                        j = b.p.treeReader.level_field,
                        d = b.p.treeReader.loaded;
                    e.level = c[j];
                    if (b.p.treeGridModel == "nested") {
                        var l = c[b.p.treeReader.left_field],
                            k = c[b.p.treeReader.right_field];
                        c[h] || (c[h] = parseInt(k, 10) === parseInt(l, 10) + 1 ? "true" : "false")
                    }
                    k = parseInt(c[j], 10);
                    if (b.p.tree_root_level === 0) {
                        l = k + 1;
                        k = k
                    } else {
                        l = k;
                        k = k - 1
                    }
                    l = "<div class='tree-wrap tree-wrap-" + b.p.direction + "' style='width:" + l * 18 + "px;'>";
                    l += "<div style='" + (b.p.direction == "rtl" ? "right:" : "left:") + k * 18 + "px;' class='ui-icon ";
                    if (c[d] != undefined) c[d] = c[d] == "true" || c[d] === true ? true : false;
                    if (c[h] == "true" || c[h] === true) {
                        l += b.p.treeIcons.leaf + " tree-leaf'";
                        c[h] = true;
                        c[g] = false
                    } else {
                        if (c[g] == "true" || c[g] === true) {
                            l += b.p.treeIcons.minus + " tree-minus treeclick'";
                            c[g] = true
                        } else {
                            l += b.p.treeIcons.plus + " tree-plus treeclick'";
                            c[g] = false
                        }
                        c[h] = false
                    }
                    l += "</div></div>";
                    if (!b.p.loadonce) {
                        c[b.p.localReader.id] =
                        e.id;
                        b.p.data.push(c);
                        b.p._index[e.id] = b.p.data.length - 1
                    }
                    if (parseInt(c[j], 10) !== parseInt(b.p.tree_root_level, 10)) a(b).jqGrid("isVisibleNode", c) || a(e).css("display", "none");
                    a("td:eq(" + f + ")", e).wrapInner("<span></span>").prepend(l);
                    a(".treeclick", e).bind("click", function (m) {
                        m = a(m.target || m.srcElement, b.rows).closest("tr.jqgrow")[0].id;
                        m = b.p._index[m];
                        var q = b.p.treeReader.expanded_field;
                        if (!b.p.data[m][b.p.treeReader.leaf_field]) if (b.p.data[m][q]) {
                            a(b).jqGrid("collapseRow", b.p.data[m]);
                            a(b).jqGrid("collapseNode", b.p.data[m])
                        } else {
                            a(b).jqGrid("expandRow", b.p.data[m]);
                            a(b).jqGrid("expandNode", b.p.data[m])
                        }
                        return false
                    });
                    b.p.ExpandColClick === true && a("span", e).css("cursor", "pointer").bind("click", function (m) {
                        m = a(m.target || m.srcElement, b.rows).closest("tr.jqgrow")[0].id;
                        var q = b.p._index[m],
                            p = b.p.treeReader.expanded_field;
                        if (!b.p.data[q][b.p.treeReader.leaf_field]) if (b.p.data[q][p]) {
                            a(b).jqGrid("collapseRow", b.p.data[q]);
                            a(b).jqGrid("collapseNode", b.p.data[q])
                        } else {
                            a(b).jqGrid("expandRow", b.p.data[q]);
                            a(b).jqGrid("expandNode", b.p.data[q])
                        }
                        a(b).jqGrid("setSelection", m);
                        return false
                    })
                }
            })
        },
        setTreeGrid: function () {
            return this.each(function () {
                var c = this,
                    e = 0;
                if (c.p.treeGrid) {
                    c.p.treedatatype || a.extend(c.p, {
                        treedatatype: c.p.datatype
                    });
                    c.p.subGrid = false;
                    c.p.altRows = false;
                    c.p.pgbuttons = false;
                    c.p.pginput = false;
                    c.p.multiselect = false;
                    c.p.rowList = [];
                    c.p.treeIcons = a.extend({
                        plus: "ui-icon-triangle-1-" + (c.p.direction == "rtl" ? "w" : "e"),
                        minus: "ui-icon-triangle-1-s",
                        leaf: "ui-icon-radio-off"
                    }, c.p.treeIcons || {});
                    if (c.p.treeGridModel == "nested") c.p.treeReader =
                    a.extend({
                        level_field: "level",
                        left_field: "lft",
                        right_field: "rgt",
                        leaf_field: "isLeaf",
                        expanded_field: "expanded",
                        loaded: "loaded"
                    }, c.p.treeReader);
                    else if (c.p.treeGridModel == "adjacency") c.p.treeReader = a.extend({
                        level_field: "level",
                        parent_id_field: "parent",
                        leaf_field: "isLeaf",
                        expanded_field: "expanded",
                        loaded: "loaded"
                    }, c.p.treeReader);
                    for (var b in c.p.colModel) if (c.p.colModel.hasOwnProperty(b)) {
                        if (c.p.colModel[b].name == c.p.ExpandColumn) {
                            c.p.expColInd = e;
                            break
                        }
                        e++
                    }
                    if (!c.p.expColInd) c.p.expColInd = 0;
                    a.each(c.p.treeReader, function (f, g) {
                        if (g) {
                            c.p.colNames.push(g);
                            c.p.colModel.push({
                                name: g,
                                width: 1,
                                hidden: true,
                                sortable: false,
                                resizable: false,
                                hidedlg: true,
                                editable: true,
                                search: false
                            })
                        }
                    })
                }
            })
        },
        expandRow: function (c) {
            this.each(function () {
                var e = this;
                if (e.grid && e.p.treeGrid) {
                    var b = a(e).jqGrid("getNodeChildren", c),
                        f = e.p.treeReader.expanded_field;
                    a(b).each(function () {
                        var g = a.jgrid.getAccessor(this, e.p.localReader.id);
                        a("#" + g, e.grid.bDiv).css("display", "");
                        this[f] && a(e).jqGrid("expandRow", this)
                    })
                }
            })
        },
        collapseRow: function (c) {
            this.each(function () {
                var e =
                this;
                if (e.grid && e.p.treeGrid) {
                    var b = a(e).jqGrid("getNodeChildren", c),
                        f = e.p.treeReader.expanded_field;
                    a(b).each(function () {
                        var g = a.jgrid.getAccessor(this, e.p.localReader.id);
                        a("#" + g, e.grid.bDiv).css("display", "none");
                        this[f] && a(e).jqGrid("collapseRow", this)
                    })
                }
            })
        },
        getRootNodes: function () {
            var c = [];
            this.each(function () {
                var e = this;
                if (e.grid && e.p.treeGrid) switch (e.p.treeGridModel) {
                case "nested":
                    var b = e.p.treeReader.level_field;
                    a(e.p.data).each(function () {
                        parseInt(this[b], 10) === parseInt(e.p.tree_root_level, 10) && c.push(this)
                    });
                    break;
                case "adjacency":
                    var f = e.p.treeReader.parent_id_field;
                    a(e.p.data).each(function () {
                        if (this[f] === null || String(this[f]).toLowerCase() == "null") c.push(this)
                    });
                    break
                }
            });
            return c
        },
        getNodeDepth: function (c) {
            var e = null;
            this.each(function () {
                if (this.grid && this.p.treeGrid) {
                    var b = this;
                    switch (b.p.treeGridModel) {
                    case "nested":
                        e = parseInt(c[b.p.treeReader.level_field], 10) - parseInt(b.p.tree_root_level, 10);
                        break;
                    case "adjacency":
                        e = a(b).jqGrid("getNodeAncestors", c).length;
                        break
                    }
                }
            });
            return e
        },
        getNodeParent: function (c) {
            var e = null;
            this.each(function () {
                var b = this;
                if (b.grid && b.p.treeGrid) switch (b.p.treeGridModel) {
                case "nested":
                    var f = b.p.treeReader.left_field,
                        g = b.p.treeReader.right_field,
                        h = b.p.treeReader.level_field,
                        j = parseInt(c[f], 10),
                        d = parseInt(c[g], 10),
                        l = parseInt(c[h], 10);
                    a(this.p.data).each(function () {
                        if (parseInt(this[h], 10) === l - 1 && parseInt(this[f], 10) < j && parseInt(this[g], 10) > d) {
                            e = this;
                            return false
                        }
                    });
                    break;
                case "adjacency":
                    var k = b.p.treeReader.parent_id_field,
                        m = b.p.localReader.id;
                    a(this.p.data).each(function () {
                        if (this[m] == c[k]) {
                            e = this;
                            return false
                        }
                    });
                    break
                }
            });
            return e
        },
        getNodeChildren: function (c) {
            var e = [];
            this.each(function () {
                var b = this;
                if (b.grid && b.p.treeGrid) switch (b.p.treeGridModel) {
                case "nested":
                    var f = b.p.treeReader.left_field,
                        g = b.p.treeReader.right_field,
                        h = b.p.treeReader.level_field,
                        j = parseInt(c[f], 10),
                        d = parseInt(c[g], 10),
                        l = parseInt(c[h], 10);
                    a(this.p.data).each(function () {
                        parseInt(this[h], 10) === l + 1 && parseInt(this[f], 10) > j && parseInt(this[g], 10) < d && e.push(this)
                    });
                    break;
                case "adjacency":
                    var k = b.p.treeReader.parent_id_field,
                        m = b.p.localReader.id;
                    a(this.p.data).each(function () {
                        this[k] == c[m] && e.push(this)
                    });
                    break
                }
            });
            return e
        },
        getFullTreeNode: function (c) {
            var e = [];
            this.each(function () {
                var b = this,
                    f;
                if (b.grid && b.p.treeGrid) switch (b.p.treeGridModel) {
                case "nested":
                    var g = b.p.treeReader.left_field,
                        h = b.p.treeReader.right_field,
                        j = b.p.treeReader.level_field,
                        d = parseInt(c[g], 10),
                        l = parseInt(c[h], 10),
                        k = parseInt(c[j], 10);
                    a(this.p.data).each(function () {
                        parseInt(this[j], 10) >= k && parseInt(this[g], 10) >= d && parseInt(this[g], 10) <= l && e.push(this)
                    });
                    break;
                case "adjacency":
                    e.push(c);
                    var m = b.p.treeReader.parent_id_field,
                        q = b.p.localReader.id;
                    a(this.p.data).each(function (p) {
                        f = e.length;
                        for (p = 0; p < f; p++) if (e[p][q] == this[m]) {
                            e.push(this);
                            break
                        }
                    });
                    break
                }
            });
            return e
        },
        getNodeAncestors: function (c) {
            var e = [];
            this.each(function () {
                if (this.grid && this.p.treeGrid) for (var b = a(this).jqGrid("getNodeParent", c); b;) {
                    e.push(b);
                    b = a(this).jqGrid("getNodeParent", b)
                }
            });
            return e
        },
        isVisibleNode: function (c) {
            var e = true;
            this.each(function () {
                var b = this;
                if (b.grid && b.p.treeGrid) {
                    var f =
                    a(b).jqGrid("getNodeAncestors", c),
                        g = b.p.treeReader.expanded_field;
                    a(f).each(function () {
                        e = e && this[g];
                        if (!e) return false
                    })
                }
            });
            return e
        },
        isNodeLoaded: function (c) {
            var e;
            this.each(function () {
                var b = this;
                if (b.grid && b.p.treeGrid) {
                    var f = b.p.treeReader.leaf_field;
                    e = c.loaded !== undefined ? c.loaded : c[f] || a(b).jqGrid("getNodeChildren", c).length > 0 ? true : false
                }
            });
            return e
        },
        expandNode: function (c) {
            return this.each(function () {
                if (this.grid && this.p.treeGrid) {
                    var e = this.p.treeReader.expanded_field;
                    if (!c[e]) {
                        var b = a.jgrid.getAccessor(c, this.p.localReader.id),
                            f = a("#" + b, this.grid.bDiv)[0],
                            g = this.p._index[b];
                        if (a(this).jqGrid("isNodeLoaded", this.p.data[g])) {
                            c[e] = true;
                            a("div.treeclick", f).removeClass(this.p.treeIcons.plus + " tree-plus").addClass(this.p.treeIcons.minus + " tree-minus")
                        } else {
                            c[e] = true;
                            a("div.treeclick", f).removeClass(this.p.treeIcons.plus + " tree-plus").addClass(this.p.treeIcons.minus + " tree-minus");
                            this.p.treeANode = f.rowIndex;
                            this.p.datatype = this.p.treedatatype;
                            this.p.treeGridModel == "nested" ? a(this).jqGrid("setGridParam", {
                                postData: {
                                    nodeid: b,
                                    n_left: c.lft,
                                    n_right: c.rgt,
                                    n_level: c.level
                                }
                            }) : a(this).jqGrid("setGridParam", {
                                postData: {
                                    nodeid: b,
                                    parentid: c.parent_id,
                                    n_level: c.level
                                }
                            });
                            a(this).trigger("reloadGrid");
                            this.p.treeGridModel == "nested" ? a(this).jqGrid("setGridParam", {
                                postData: {
                                    nodeid: "",
                                    n_left: "",
                                    n_right: "",
                                    n_level: ""
                                }
                            }) : a(this).jqGrid("setGridParam", {
                                postData: {
                                    nodeid: "",
                                    parentid: "",
                                    n_level: ""
                                }
                            })
                        }
                    }
                }
            })
        },
        collapseNode: function (c) {
            return this.each(function () {
                if (this.grid && this.p.treeGrid) if (c.expanded) {
                    c.expanded = false;
                    var e = a.jgrid.getAccessor(c, this.p.localReader.id);
                    e = a("#" + e, this.grid.bDiv)[0];
                    a("div.treeclick", e).removeClass(this.p.treeIcons.minus + " tree-minus").addClass(this.p.treeIcons.plus + " tree-plus")
                }
            })
        },
        SortTree: function (c, e, b, f) {
            return this.each(function () {
                if (this.grid && this.p.treeGrid) {
                    var g, h, j, d = [],
                        l = this,
                        k;
                    g = a(this).jqGrid("getRootNodes");
                    g = a.jgrid.from(g);
                    g.orderBy(c, e, b, f);
                    k = g.select();
                    g = 0;
                    for (h = k.length; g < h; g++) {
                        j = k[g];
                        d.push(j);
                        a(this).jqGrid("collectChildrenSortTree", d, j, c, e, b, f)
                    }
                    a.each(d, function (m) {
                        var q = a.jgrid.getAccessor(this, l.p.localReader.id);
                        a("#" + l.p.id + " tbody tr:eq(" + m + ")").after(a("tr#" + q, l.grid.bDiv))
                    });
                    d = k = g = null
                }
            })
        },
        collectChildrenSortTree: function (c, e, b, f, g, h) {
            return this.each(function () {
                if (this.grid && this.p.treeGrid) {
                    var j, d, l, k;
                    j = a(this).jqGrid("getNodeChildren", e);
                    j = a.jgrid.from(j);
                    j.orderBy(b, f, g, h);
                    k = j.select();
                    j = 0;
                    for (d = k.length; j < d; j++) {
                        l = k[j];
                        c.push(l);
                        a(this).jqGrid("collectChildrenSortTree", c, l, b, f, g, h)
                    }
                }
            })
        },
        setTreeRow: function (c, e) {
            var b = false;
            this.each(function () {
                var f =
                this;
                if (f.grid && f.p.treeGrid) b = a(f).jqGrid("setRowData", c, e)
            });
            return b
        },
        delTreeNode: function (c) {
            return this.each(function () {
                var e = this;
                if (e.grid && e.p.treeGrid) {
                    var b = a(e).jqGrid("getInd", c, true);
                    if (b) {
                        var f = a(e).jqGrid("getNodeChildren", b);
                        if (f.length > 0) for (var g = 0; g < f.length; g++) a(e).jqGrid("delRowData", f[g].id);
                        a(e).jqGrid("delRowData", b.id)
                    }
                }
            })
        }
    })
})(jQuery);
(function (a) {
    a.fn.jqDrag = function (j) {
        return g(this, j, "d")
    };
    a.fn.jqResize = function (j, d) {
        return g(this, j, "r", d)
    };
    a.jqDnR = {
        dnr: {},
        e: 0,
        drag: function (j) {
            if (e.k == "d") b.css({
                left: e.X + j.pageX - e.pX,
                top: e.Y + j.pageY - e.pY
            });
            else {
                b.css({
                    width: Math.max(j.pageX - e.pX + e.W, 0),
                    height: Math.max(j.pageY - e.pY + e.H, 0)
                });
                M1 && f.css({
                    width: Math.max(j.pageX - M1.pX + M1.W, 0),
                    height: Math.max(j.pageY - M1.pY + M1.H, 0)
                })
            }
            return false
        },
        stop: function () {
            a(document).unbind("mousemove", c.drag).unbind("mouseup", c.stop)
        }
    };
    var c = a.jqDnR,
        e = c.dnr,
        b = c.e,
        f, g = function (j, d, l, k) {
            return j.each(function () {
                d = d ? a(d, j) : j;
                d.bind("mousedown", {
                    e: j,
                    k: l
                }, function (m) {
                    var q = m.data,
                        p = {};
                    b = q.e;
                    f = k ? a(k) : false;
                    if (b.css("position") != "relative") try {
                        b.position(p)
                    } catch (u) {}
                    e = {
                        X: p.left || h("left") || 0,
                        Y: p.top || h("top") || 0,
                        W: h("width") || b[0].scrollWidth || 0,
                        H: h("height") || b[0].scrollHeight || 0,
                        pX: m.pageX,
                        pY: m.pageY,
                        k: q.k
                    };
                    M1 = f && q.k != "d" ? {
                        X: p.left || f1("left") || 0,
                        Y: p.top || f1("top") || 0,
                        W: f[0].offsetWidth || f1("width") || 0,
                        H: f[0].offsetHeight || f1("height") || 0,
                        pX: m.pageX,
                        pY: m.pageY,
                        k: q.k
                    } : false;
                    try {
                        a("input.hasDatepicker", b[0]).datepicker("hide")
                    } catch (s) {}
                    a(document).mousemove(a.jqDnR.drag).mouseup(a.jqDnR.stop);
                    return false
                })
            })
        },
        h = function (j) {
            return parseInt(b.css(j)) || false
        };
    f1 = function (j) {
        return parseInt(f.css(j)) || false
    }
})(jQuery);
(function (a) {
    a.fn.jqm = function (m) {
        var q = {
            overlay: 50,
            closeoverlay: true,
            overlayClass: "jqmOverlay",
            closeClass: "jqmClose",
            trigger: ".jqModal",
            ajax: g,
            ajaxText: "",
            target: g,
            modal: g,
            toTop: g,
            onShow: g,
            onHide: g,
            onLoad: g
        };
        return this.each(function () {
            if (this._jqm) return e[this._jqm].c = a.extend({}, e[this._jqm].c, m);
            c++;
            this._jqm = c;
            e[c] = {
                c: a.extend(q, a.jqm.params, m),
                a: g,
                w: a(this).addClass("jqmID" + c),
                s: c
            };
            q.trigger && a(this).jqmAddTrigger(q.trigger)
        })
    };
    a.fn.jqmAddClose = function (m) {
        return k(this, m, "jqmHide")
    };
    a.fn.jqmAddTrigger =

    function (m) {
        return k(this, m, "jqmShow")
    };
    a.fn.jqmShow = function (m) {
        return this.each(function () {
            a.jqm.open(this._jqm, m)
        })
    };
    a.fn.jqmHide = function (m) {
        return this.each(function () {
            a.jqm.close(this._jqm, m)
        })
    };
    a.jqm = {
        hash: {},
        open: function (m, q) {
            var p = e[m],
                u = p.c,
                s = "." + u.closeClass,
                n = parseInt(p.w.css("z-index"));
            n = n > 0 ? n : 3E3;
            var o = a("<div></div>").css({
                height: "100%",
                width: "100%",
                position: "fixed",
                left: 0,
                top: 0,
                "z-index": n - 1,
                opacity: u.overlay / 100
            });
            if (p.a) return g;
            p.t = q;
            p.a = true;
            p.w.css("z-index", n);
            if (u.modal) {
                b[0] || setTimeout(function () {
                    d("bind")
                }, 1);
                b.push(m)
            } else if (u.overlay > 0) u.closeoverlay && p.w.jqmAddClose(o);
            else o = g;
            p.o = o ? o.addClass(u.overlayClass).prependTo("body") : g;
            if (f) {
                a("html,body").css({
                    height: "100%",
                    width: "100%"
                });
                if (o) {
                    o = o.css({
                        position: "absolute"
                    })[0];
                    for (var r in {
                        Top: 1,
                        Left: 1
                    }) o.style.setExpression(r.toLowerCase(), "(_=(document.documentElement.scroll" + r + " || document.body.scroll" + r + "))+'px'")
                }
            }
            if (u.ajax) {
                m = u.target || p.w;
                n = u.ajax;
                m = typeof m == "string" ? a(m, p.w) : a(m);
                n = n.substr(0, 1) == "@" ? a(q).attr(n.substring(1)) : n;
                m.html(u.ajaxText).load(n, function () {
                    u.onLoad && u.onLoad.call(this, p);
                    s && p.w.jqmAddClose(a(s, p.w));
                    h(p)
                })
            } else s && p.w.jqmAddClose(a(s, p.w));
            u.toTop && p.o && p.w.before('<span id="jqmP' + p.w[0]._jqm + '"></span>').insertAfter(p.o);
            u.onShow ? u.onShow(p) : p.w.show();
            h(p);
            return g
        },
        close: function (m) {
            m = e[m];
            if (!m.a) return g;
            m.a = g;
            if (b[0]) {
                b.pop();
                b[0] || d("unbind")
            }
            m.c.toTop && m.o && a("#jqmP" + m.w[0]._jqm).after(m.w).remove();
            if (m.c.onHide) m.c.onHide(m);
            else {
                m.w.hide();
                m.o && m.o.remove()
            }
            return g
        },
        params: {}
    };
    var c =
    0,
        e = a.jqm.hash,
        b = [],
        f = a.browser.msie && a.browser.version == "6.0",
        g = false,
        h = function (m) {
            var q = a('<iframe src="javascript:false;document.write(\'\');" class="jqm"></iframe>').css({
                opacity: 0
            });
            if (f) if (m.o) m.o.html('<p style="width:100%;height:100%"/>').prepend(q);
            else a("iframe.jqm", m.w)[0] || m.w.prepend(q);
            j(m)
        },
        j = function (m) {
            try {
                a(":input:visible", m.w)[0].focus()
            } catch (q) {}
        },
        d = function (m) {
            a(document)[m]("keypress", l)[m]("keydown", l)[m]("mousedown", l)
        },
        l = function (m) {
            var q = e[b[b.length - 1]];
            (m = !a(m.target).parents(".jqmID" + q.s)[0]) && j(q);
            return !m
        },
        k = function (m, q, p) {
            return m.each(function () {
                var u = this._jqm;
                a(q).each(function () {
                    if (!this[p]) {
                        this[p] = [];
                        a(this).click(function () {
                            for (var s in {
                                jqmShow: 1,
                                jqmHide: 1
                            }) for (var n in this[s]) e[this[s][n]] && e[this[s][n]].w[s](this);
                            return g
                        })
                    }
                    this[p].push(u)
                })
            })
        }
})(jQuery);
(function (a) {
    a.fmatter = {};
    a.extend(a.fmatter, {
        isBoolean: function (c) {
            return typeof c === "boolean"
        },
        isObject: function (c) {
            return c && (typeof c === "object" || a.isFunction(c)) || false
        },
        isString: function (c) {
            return typeof c === "string"
        },
        isNumber: function (c) {
            return typeof c === "number" && isFinite(c)
        },
        isNull: function (c) {
            return c === null
        },
        isUndefined: function (c) {
            return typeof c === "undefined"
        },
        isValue: function (c) {
            return this.isObject(c) || this.isString(c) || this.isNumber(c) || this.isBoolean(c)
        },
        isEmpty: function (c) {
            if (!this.isString(c) && this.isValue(c)) return false;
            else if (!this.isValue(c)) return true;
            c = a.trim(c).replace(/\&nbsp\;/ig, "").replace(/\&#160\;/ig, "");
            return c === ""
        }
    });
    a.fn.fmatter = function (c, e, b, f, g) {
        var h = e;
        b = a.extend({}, a.jgrid.formatter, b);
        if (a.fn.fmatter[c]) h = a.fn.fmatter[c](e, b, f, g);
        return h
    };
    a.fmatter.util = {
        NumberFormat: function (c, e) {
            a.fmatter.isNumber(c) || (c *= 1);
            if (a.fmatter.isNumber(c)) {
                var b = c < 0,
                    f = c + "",
                    g = e.decimalSeparator ? e.decimalSeparator : ".";
                if (a.fmatter.isNumber(e.decimalPlaces)) {
                    var h = e.decimalPlaces;
                    f =
                    Math.pow(10, h);
                    f = Math.round(c * f) / f + "";
                    c = f.lastIndexOf(".");
                    if (h > 0) {
                        if (c < 0) {
                            f += g;
                            c = f.length - 1
                        } else if (g !== ".") f = f.replace(".", g);
                        for (; f.length - 1 - c < h;) f += "0"
                    }
                }
                if (e.thousandsSeparator) {
                    h = e.thousandsSeparator;
                    c = f.lastIndexOf(g);
                    c = c > -1 ? c : f.length;
                    g = f.substring(c);
                    for (var j = -1, d = c; d > 0; d--) {
                        j++;
                        if (j % 3 === 0 && d !== c && (!b || d > 1)) g = h + g;
                        g = f.charAt(d - 1) + g
                    }
                    f = g
                }
                f = e.prefix ? e.prefix + f : f;
                return f = e.suffix ? f + e.suffix : f
            } else
            return c
        },
        DateFormat: function (c, e, b, f) {
            var g = /^\/Date((([-+])?[0-9]+)(([-+])([0-9]{2})([0-9]{2}))?)\/$/,
                h = typeof e === "string" ? e.match(g) : null;
            g = function (r, C) {
                r = String(r);
                for (C = parseInt(C, 10) || 2; r.length < C;) r = "0" + r;
                return r
            };
            var j = {
                m: 1,
                d: 1,
                y: 1970,
                h: 0,
                i: 0,
                s: 0,
                u: 0
            },
                d = 0,
                l, k = ["i18n"];
            k.i18n = {
                dayNames: f.dayNames,
                monthNames: f.monthNames
            };
            if (c in f.masks) c = f.masks[c];
            if (e.constructor === Number) {
                if (String(c).toLowerCase() == "u") e *= 1E3;
                d = new Date(e)
            } else if (e.constructor === Date) d = e;
            else if (h !== null) {
                d = new Date(parseInt(h[1], 10));
                if (h[3]) {
                    c = Number(h[5]) * 60 + Number(h[6]);
                    c *= h[4] == "-" ? 1 : -1;
                    c -= d.getTimezoneOffset();
                    d.setTime(Number(Number(d) + c * 60 * 1E3))
                }
            } else {
                e = String(e).split(/[\\\/:_;.,\t\T\s-]/);
                c = c.split(/[\\\/:_;.,\t\T\s-]/);
                h = 0;
                for (l = c.length; h < l; h++) {
                    if (c[h] == "M") {
                        d = a.inArray(e[h], k.i18n.monthNames);
                        if (d !== -1 && d < 12) e[h] = d + 1
                    }
                    if (c[h] == "F") {
                        d = a.inArray(e[h], k.i18n.monthNames);
                        if (d !== -1 && d > 11) e[h] = d + 1 - 12
                    }
                    if (e[h]) j[c[h].toLowerCase()] = parseInt(e[h], 10)
                }
                if (j.f) j.m = j.f;
                if (j.m === 0 && j.y === 0 && j.d === 0) return "&#160;";
                j.m = parseInt(j.m, 10) - 1;
                d = j.y;
                if (d >= 70 && d <= 99) j.y = 1900 + j.y;
                else if (d >= 0 && d <= 69) j.y = 2E3 + j.y;
                d = new Date(j.y, j.m, j.d, j.h, j.i, j.s, j.u)
            }
            if (b in f.masks) b = f.masks[b];
            else b || (b = "Y-m-d");
            c = d.getHours();
            e = d.getMinutes();
            j = d.getDate();
            h = d.getMonth() + 1;
            l = d.getTimezoneOffset();
            var m = d.getSeconds(),
                q = d.getMilliseconds(),
                p = d.getDay(),
                u = d.getFullYear(),
                s = (p + 6) % 7 + 1,
                n = (new Date(u, h - 1, j) - new Date(u, 0, 1)) / 864E5,
                o = {
                    d: g(j),
                    D: k.i18n.dayNames[p],
                    j: j,
                    l: k.i18n.dayNames[p + 7],
                    N: s,
                    S: f.S(j),
                    w: p,
                    z: n,
                    W: s < 5 ? Math.floor((n + s - 1) / 7) + 1 : Math.floor((n + s - 1) / 7) || (((new Date(u - 1, 0, 1)).getDay() + 6) % 7 < 4 ? 53 : 52),
                    F: k.i18n.monthNames[h - 1 + 12],
                    m: g(h),
                    M: k.i18n.monthNames[h - 1],
                    n: h,
                    t: "?",
                    L: "?",
                    o: "?",
                    Y: u,
                    y: String(u).substring(2),
                    a: c < 12 ? f.AmPm[0] : f.AmPm[1],
                    A: c < 12 ? f.AmPm[2] : f.AmPm[3],
                    B: "?",
                    g: c % 12 || 12,
                    G: c,
                    h: g(c % 12 || 12),
                    H: g(c),
                    i: g(e),
                    s: g(m),
                    u: q,
                    e: "?",
                    I: "?",
                    O: (l > 0 ? "-" : "+") + g(Math.floor(Math.abs(l) / 60) * 100 + Math.abs(l) % 60, 4),
                    P: "?",
                    T: (String(d).match(/\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g) || [""]).pop().replace(/[^-+\dA-Z]/g, ""),
                    Z: "?",
                    c: "?",
                    r: "?",
                    U: Math.floor(d / 1E3)
                };
            return b.replace(/\\.|[dDjlNSwzWFmMntLoYyaABgGhHisueIOPTZcrU]/g, function (r) {
                return r in o ? o[r] : r.substring(1)
            })
        }
    };
    a.fn.fmatter.defaultFormat = function (c, e) {
        return a.fmatter.isValue(c) && c !== "" ? c : e.defaultValue ? e.defaultValue : "&#160;"
    };
    a.fn.fmatter.email = function (c, e) {
        return a.fmatter.isEmpty(c) ? a.fn.fmatter.defaultFormat(c, e) : '<a href="mailto:' + c + '">' + c + "</a>"
    };
    a.fn.fmatter.checkbox = function (c, e) {
        var b = a.extend({}, e.checkbox);
        a.fmatter.isUndefined(e.colModel.formatoptions) || (b = a.extend({}, b, e.colModel.formatoptions));
        e = b.disabled === true ? 'disabled="disabled"' : "";
        if (a.fmatter.isEmpty(c) || a.fmatter.isUndefined(c)) c = a.fn.fmatter.defaultFormat(c, b);
        c += "";
        c = c.toLowerCase();
        return '<input type="checkbox" ' + (c.search(/(false|0|no|off)/i) < 0 ? " checked='checked' " : "") + ' value="' + c + '" offval="no" ' + e + "/>"
    };
    a.fn.fmatter.link = function (c, e) {
        var b = {
            target: e.target
        },
            f = "";
        a.fmatter.isUndefined(e.colModel.formatoptions) || (b = a.extend({}, b, e.colModel.formatoptions));
        if (b.target) f = "target=" + b.target;
        return a.fmatter.isEmpty(c) ? a.fn.fmatter.defaultFormat(c, e) : "<a " + f + ' href="' + c + '">' + c + "</a>"
    };
    a.fn.fmatter.showlink = function (c, e) {
        var b = {
            baseLinkUrl: e.baseLinkUrl,
            showAction: e.showAction,
            addParam: e.addParam || "",
            target: e.target,
            idName: e.idName
        },
            f = "";
        a.fmatter.isUndefined(e.colModel.formatoptions) || (b = a.extend({}, b, e.colModel.formatoptions));
        if (b.target) f = "target=" + b.target;
        b = b.baseLinkUrl + b.showAction + "?" + b.idName + "=" + e.rowId + b.addParam;
        return a.fmatter.isString(c) || a.fmatter.isNumber(c) ? "<a " + f + ' href="' + b + '">' + c + "</a>" : a.fn.fmatter.defaultFormat(c, e)
    };
    a.fn.fmatter.integer = function (c, e) {
        var b = a.extend({}, e.integer);
        a.fmatter.isUndefined(e.colModel.formatoptions) || (b = a.extend({}, b, e.colModel.formatoptions));
        if (a.fmatter.isEmpty(c)) return b.defaultValue;
        return a.fmatter.util.NumberFormat(c, b)
    };
    a.fn.fmatter.number = function (c, e) {
        var b = a.extend({}, e.number);
        a.fmatter.isUndefined(e.colModel.formatoptions) || (b = a.extend({}, b, e.colModel.formatoptions));
        if (a.fmatter.isEmpty(c)) return b.defaultValue;
        return a.fmatter.util.NumberFormat(c, b)
    };
    a.fn.fmatter.currency =

    function (c, e) {
        var b = a.extend({}, e.currency);
        a.fmatter.isUndefined(e.colModel.formatoptions) || (b = a.extend({}, b, e.colModel.formatoptions));
        if (a.fmatter.isEmpty(c)) return b.defaultValue;
        return a.fmatter.util.NumberFormat(c, b)
    };
    a.fn.fmatter.date = function (c, e, b, f) {
        b = a.extend({}, e.date);
        a.fmatter.isUndefined(e.colModel.formatoptions) || (b = a.extend({}, b, e.colModel.formatoptions));
        return !b.reformatAfterEdit && f == "edit" ? a.fn.fmatter.defaultFormat(c, e) : a.fmatter.isEmpty(c) ? a.fn.fmatter.defaultFormat(c, e) : a.fmatter.util.DateFormat(b.srcformat, c, b.newformat, b)
    };
    a.fn.fmatter.select = function (c, e) {
        c += "";
        var b = false,
            f = [];
        if (a.fmatter.isUndefined(e.colModel.formatoptions)) {
            if (!a.fmatter.isUndefined(e.colModel.editoptions)) b = e.colModel.editoptions.value
        } else b = e.colModel.formatoptions.value;
        if (b) {
            var g = e.colModel.editoptions.multiple === true ? true : false,
                h = [],
                j;
            if (g) {
                h = c.split(",");
                h = a.map(h, function (m) {
                    return a.trim(m)
                })
            }
            if (a.fmatter.isString(b)) for (var d = b.split(";"), l = 0, k = 0; k < d.length; k++) {
                j = d[k].split(":");
                if (j.length > 2) j[1] = jQuery.map(j, function (m, q) {
                    if (q > 0) return m
                }).join(":");
                if (g) {
                    if (jQuery.inArray(j[0], h) > -1) {
                        f[l] = j[1];
                        l++
                    }
                } else if (a.trim(j[0]) == a.trim(c)) {
                    f[0] = j[1];
                    break
                }
            } else if (a.fmatter.isObject(b)) if (g) f = jQuery.map(h, function (m) {
                return b[m]
            });
            else f[0] = b[c] || ""
        }
        c = f.join(", ");
        return c === "" ? a.fn.fmatter.defaultFormat(c, e) : c
    };
    a.fn.fmatter.rowactions = function (c, e, b, f) {
        var g = {
            keys: false,
            editbutton: true,
            delbutton: true,
            onEdit: null,
            onSuccess: null,
            afterSave: null,
            onError: null,
            afterRestore: null,
            extraparam: {
                oper: "edit"
            },
            url: null,
            delOptions: {}
        };
        f = a("#" + e)[0].p.colModel[f];
        a.fmatter.isUndefined(f.formatoptions) || (g = a.extend(g, f.formatoptions));
        f = function (j) {
            g.afterSave && g.afterSave(j);
            a("tr#" + c + " div.ui-inline-edit, tr#" + c + " div.ui-inline-del", "#" + e).show();
            a("tr#" + c + " div.ui-inline-save, tr#" + c + " div.ui-inline-cancel", "#" + e).hide()
        };
        var h = function (j) {
            g.afterRestore && g.afterRestore(j);
            a("tr#" + c + " div.ui-inline-edit, tr#" + c + " div.ui-inline-del", "#" + e).show();
            a("tr#" + c + " div.ui-inline-save, tr#" + c + " div.ui-inline-cancel", "#" + e).hide()
        };
        switch (b) {
        case "edit":
            a("#" + e).jqGrid("editRow", c, g.keys, g.onEdit, g.onSuccess, g.url, g.extraparam, f, g.onError, h);
            a("tr#" + c + " div.ui-inline-edit, tr#" + c + " div.ui-inline-del", "#" + e).hide();
            a("tr#" + c + " div.ui-inline-save, tr#" + c + " div.ui-inline-cancel", "#" + e).show();
            break;
        case "save":
            a("#" + e).jqGrid("saveRow", c, g.onSuccess, g.url, g.extraparam, f, g.onError, h);
            a("tr#" + c + " div.ui-inline-edit, tr#" + c + " div.ui-inline-del", "#" + e).show();
            a("tr#" + c + " div.ui-inline-save, tr#" + c + " div.ui-inline-cancel", "#" + e).hide();
            break;
        case "cancel":
            a("#" + e).jqGrid("restoreRow", c, h);
            a("tr#" + c + " div.ui-inline-edit, tr#" + c + " div.ui-inline-del", "#" + e).show();
            a("tr#" + c + " div.ui-inline-save, tr#" + c + " div.ui-inline-cancel", "#" + e).hide();
            break;
        case "del":
            a("#" + e).jqGrid("delGridRow", c, g.delOptions);
            break
        }
    };
    a.fn.fmatter.actions = function (c, e) {
        c = {
            keys: false,
            editbutton: true,
            delbutton: true
        };
        a.fmatter.isUndefined(e.colModel.formatoptions) || (c = a.extend(c, e.colModel.formatoptions));
        var b = e.rowId,
            f = "",
            g;
        if (typeof b == "undefined" || a.fmatter.isEmpty(b)) return "";
        if (c.editbutton) {
            g = "onclick=$.fn.fmatter.rowactions('" + b + "','" + e.gid + "','edit'," + e.pos + ");";
            f = f + "<div style='margin-left:8px;'><div title='" + a.jgrid.nav.edittitle + "' style='float:left;cursor:pointer;' class='ui-pg-div ui-inline-edit' " + g + "><span class='ui-icon ui-icon-pencil'></span></div>"
        }
        if (c.delbutton) {
            g = "onclick=$.fn.fmatter.rowactions('" + b + "','" + e.gid + "','del'," + e.pos + ");";
            f = f + "<div title='" + a.jgrid.nav.deltitle + "' style='float:left;margin-left:5px;' class='ui-pg-div ui-inline-del' " + g + "><span class='ui-icon ui-icon-trash'></span></div>"
        }
        g = "onclick=$.fn.fmatter.rowactions('" + b + "','" + e.gid + "','save'," + e.pos + ");";
        f = f + "<div title='" + a.jgrid.edit.bSubmit + "' style='float:left;display:none' class='ui-pg-div ui-inline-save'><span class='ui-icon ui-icon-disk' " + g + "></span></div>";
        g = "onclick=$.fn.fmatter.rowactions('" + b + "','" + e.gid + "','cancel'," + e.pos + ");";
        return f = f + "<div title='" + a.jgrid.edit.bCancel + "' style='float:left;display:none;margin-left:5px;' class='ui-pg-div ui-inline-cancel'><span class='ui-icon ui-icon-cancel' " + g + "></span></div></div>"
    };
    a.unformat = function (c, e, b, f) {
        var g, h = e.colModel.formatter,
            j = e.colModel.formatoptions || {},
            d = /([\.\*\_\'\(\)\{\}\+\?\\])/g,
            l = e.colModel.unformat || a.fn.fmatter[h] && a.fn.fmatter[h].unformat;
        if (typeof l !== "undefined" && a.isFunction(l)) g = l(a(c).text(), e, c);
        else if (!a.fmatter.isUndefined(h) && a.fmatter.isString(h)) {
            g = a.jgrid.formatter || {};
            switch (h) {
            case "integer":
                j = a.extend({}, g.integer, j);
                e = j.thousandsSeparator.replace(d, "\\$1");
                e = new RegExp(e, "g");
                g = a(c).text().replace(e, "");
                break;
            case "number":
                j = a.extend({}, g.number, j);
                e = j.thousandsSeparator.replace(d, "\\$1");
                e = new RegExp(e, "g");
                g = a(c).text().replace(e, "").replace(j.decimalSeparator, ".");
                break;
            case "currency":
                j = a.extend({}, g.currency, j);
                e = j.thousandsSeparator.replace(d, "\\$1");
                e = new RegExp(e, "g");
                g = a(c).text().replace(e, "").replace(j.decimalSeparator, ".").replace(j.prefix, "").replace(j.suffix, "");
                break;
            case "checkbox":
                j = e.colModel.editoptions ? e.colModel.editoptions.value.split(":") : ["Yes", "No"];
                g = a("input", c).attr("checked") ? j[0] : j[1];
                break;
            case "select":
                g =
                a.unformat.select(c, e, b, f);
                break;
            case "actions":
                return "";
            default:
                g = a(c).text()
            }
        }
        return g ? g : f === true ? a(c).text() : a.jgrid.htmlDecode(a(c).html())
    };
    a.unformat.select = function (c, e, b, f) {
        b = [];
        c = a(c).text();
        if (f === true) return c;
        e = a.extend({}, e.colModel.editoptions);
        if (e.value) {
            var g = e.value;
            e = e.multiple === true ? true : false;
            f = [];
            var h;
            if (e) {
                f = c.split(",");
                f = a.map(f, function (k) {
                    return a.trim(k)
                })
            }
            if (a.fmatter.isString(g)) for (var j = g.split(";"), d = 0, l = 0; l < j.length; l++) {
                h = j[l].split(":");
                if (h.length > 2) h[1] = jQuery.map(h, function (k, m) {
                    if (m > 0) return k
                }).join(":");
                if (e) {
                    if (jQuery.inArray(h[1], f) > -1) {
                        b[d] = h[0];
                        d++
                    }
                } else if (a.trim(h[1]) == a.trim(c)) {
                    b[0] = h[0];
                    break
                }
            } else if (a.fmatter.isObject(g) || a.isArray(g)) {
                e || (f[0] = c);
                b = jQuery.map(f, function (k) {
                    var m;
                    a.each(g, function (q, p) {
                        if (p == k) {
                            m = q;
                            return false
                        }
                    });
                    if (typeof m != "undefined") return m
                })
            }
            return b.join(", ")
        } else
        return c || ""
    };
    a.unformat.date = function (c, e) {
        var b = a.jgrid.formatter.date || {};
        a.fmatter.isUndefined(e.formatoptions) || (b = a.extend({}, b, e.formatoptions));
        return a.fmatter.isEmpty(c) ? a.fn.fmatter.defaultFormat(c, e) : a.fmatter.util.DateFormat(b.newformat, c, b.srcformat, b)
    }
})(jQuery);
jQuery.fn.searchFilter = function (a, c) {
    function e(b, f, g) {
        this.$ = b;
        this.add = function (z) {
            z == null ? b.find(".ui-add-last").click() : b.find(".sf:eq(" + z + ") .ui-add").click();
            return this
        };
        this.del = function (z) {
            z == null ? b.find(".sf:last .ui-del").click() : b.find(".sf:eq(" + z + ") .ui-del").click();
            return this
        };
        this.search = function () {
            b.find(".ui-search").click();
            return this
        };
        this.reset = function (z) {
            if (z === undefined) z = false;
            b.find(".ui-reset").trigger("click", [z]);
            return this
        };
        this.close = function () {
            b.find(".ui-closer").click();
            return this
        };
        if (f != null) {
            function h() {
                jQuery(this).toggleClass("ui-state-hover");
                return false
            }
            function j(z) {
                jQuery(this).toggleClass("ui-state-active", z.type == "mousedown");
                return false
            }
            function d(z, y) {
                return "<option value='" + z + "'>" + y + "</option>"
            }
            function l(z, y, A) {
                return "<select class='" + z + "'" + (A ? " style='display:none;'" : "") + ">" + y + "</select>"
            }
            function k(z, y) {
                z = b.find("tr.sf td.data " + z);
                z[0] != null && y(z)
            }
            function m(z, y) {
                var A = b.find("tr.sf td.data " + z);
                A[0] != null && jQuery.each(y, function () {
                    this.data != null ? A.bind(this.type, this.data, this.fn) : A.bind(this.type, this.fn)
                })
            }
            var q = jQuery.extend({}, jQuery.fn.searchFilter.defaults, g),
                p = -1,
                u = "";
            jQuery.each(q.groupOps, function () {
                u += d(this.op, this.text)
            });
            u = "<select name='groupOp'>" + u + "</select>";
            b.html("").addClass("ui-searchFilter").append("<div class='ui-widget-overlay' style='z-index: -1'>&#160;</div><table class='ui-widget-content ui-corner-all'><thead><tr><td colspan='5' class='ui-widget-header ui-corner-all' style='line-height: 18px;'><div class='ui-closer ui-state-default ui-corner-all ui-helper-clearfix' style='float: right;'><span class='ui-icon ui-icon-close'></span></div>" + q.windowTitle + "</td></tr></thead><tbody><tr class='sf'><td class='fields'></td><td class='ops'></td><td class='data'></td><td><div class='ui-del ui-state-default ui-corner-all'><span class='ui-icon ui-icon-minus'></span></div></td><td><div class='ui-add ui-state-default ui-corner-all'><span class='ui-icon ui-icon-plus'></span></div></td></tr><tr><td colspan='5' class='divider'><hr class='ui-widget-content' style='margin:1px'/></td></tr></tbody><tfoot><tr><td colspan='3'><span class='ui-reset ui-state-default ui-corner-all' style='display: inline-block; float: left;'><span class='ui-icon ui-icon-arrowreturnthick-1-w' style='float: left;'></span><span style='line-height: 18px; padding: 0 7px 0 3px;'>" + q.resetText + "</span></span><span class='ui-search ui-state-default ui-corner-all' style='display: inline-block; float: right;'><span class='ui-icon ui-icon-search' style='float: left;'></span><span style='line-height: 18px; padding: 0 7px 0 3px;'>" + q.searchText + "</span></span><span class='matchText'>" + q.matchText + "</span> " + u + " <span class='rulesText'>" + q.rulesText + "</span></td><td>&#160;</td><td><div class='ui-add-last ui-state-default ui-corner-all'><span class='ui-icon ui-icon-plusthick'></span></div></td></tr></tfoot></table>");
            var s = b.find("tr.sf"),
                n = s.find("td.fields"),
                o = s.find("td.ops"),
                r = s.find("td.data"),
                C = "";
            jQuery.each(q.operators, function () {
                C += d(this.op, this.text)
            });
            C = l("default", C, true);
            o.append(C);
            r.append("<input type='text' class='default' style='display:none;' />");
            var x = "",
                B = false,
                D = false;
            jQuery.each(f, function (z) {
                x += d(this.itemval, this.text);
                if (this.ops != null) {
                    B = true;
                    var y = "";
                    jQuery.each(this.ops, function () {
                        y += d(this.op, this.text)
                    });
                    y = l("field" + z, y, true);
                    o.append(y)
                }
                if (this.dataUrl != null) {
                    if (z > p) p = z;
                    D = true;
                    var A = this.dataEvents,
                        J = this.dataInit,
                        M = this.buildSelect;
                    jQuery.ajax(jQuery.extend({
                        url: this.dataUrl,
                        complete: function (ba) {
                            ba = M != null ? jQuery("<div />").append(M(ba)) : jQuery("<div />").append(ba.responseText);
                            ba.find("select").addClass("field" + z).hide();
                            r.append(ba.html());
                            J && k(".field" + z, J);
                            A && m(".field" + z, A);
                            z == p && b.find("tr.sf td.fields select[name='field']").change()
                        }
                    }, q.ajaxSelectOptions))
                } else if (this.dataValues != null) {
                    D = true;
                    var U = "";
                    jQuery.each(this.dataValues, function () {
                        U += d(this.value, this.text)
                    });
                    U = l("field" + z, U, true);
                    r.append(U)
                } else if (this.dataEvents != null || this.dataInit != null) {
                    D = true;
                    U = "<input type='text' class='field" + z + "' />";
                    r.append(U)
                }
                this.dataInit != null && z != p && k(".field" + z, this.dataInit);
                this.dataEvents != null && z != p && m(".field" + z, this.dataEvents)
            });
            x = "<select name='field'>" + x + "</select>";
            n.append(x);
            f = n.find("select[name='field']");
            B ? f.change(function (z) {
                var y = z.target.selectedIndex;
                z = jQuery(z.target).parents("tr.sf").find("td.ops");
                z.find("select").removeAttr("name").hide();
                y = z.find(".field" + y);
                if (y[0] == null) y = z.find(".default");
                y.attr("name", "op").show();
                return false
            }) : o.find(".default").attr("name", "op").show();
            D ? f.change(function (z) {
                var y = z.target.selectedIndex;
                z = jQuery(z.target).parents("tr.sf").find("td.data");
                z.find("select,input").removeClass("vdata").hide();
                y = z.find(".field" + y);
                if (y[0] == null) y = z.find(".default");
                y.show().addClass("vdata");
                return false
            }) : r.find(".default").show().addClass("vdata");
            if (B || D) f.change();
            b.find(".ui-state-default").hover(h, h).mousedown(j).mouseup(j);
            b.find(".ui-closer").click(function () {
                q.onClose(jQuery(b.selector));
                return false
            });
            b.find(".ui-del").click(function (z) {
                z = jQuery(z.target).parents(".sf");
                if (z.siblings(".sf").length > 0) {
                    q.datepickerFix === true && jQuery.fn.datepicker !== undefined && z.find(".hasDatepicker").datepicker("destroy");
                    z.remove()
                } else {
                    z.find("select[name='field']")[0].selectedIndex = 0;
                    z.find("select[name='op']")[0].selectedIndex = 0;
                    z.find(".data input").val("");
                    z.find(".data select").each(function () {
                        this.selectedIndex = 0
                    });
                    z.find("select[name='field']").change(function (y) {
                        y.stopPropagation()
                    })
                }
                return false
            });
            b.find(".ui-add").click(function (z) {
                z = jQuery(z.target).parents(".sf");
                var y = z.clone(true).insertAfter(z);
                y.find(".ui-state-default").removeClass("ui-state-hover ui-state-active");
                if (q.clone) {
                    y.find("select[name='field']")[0].selectedIndex = z.find("select[name='field']")[0].selectedIndex;
                    if (y.find("select[name='op']")[0] != null) y.find("select[name='op']").focus()[0].selectedIndex = z.find("select[name='op']")[0].selectedIndex;
                    var A = y.find("select.vdata");
                    if (A[0] != null) A[0].selectedIndex = z.find("select.vdata")[0].selectedIndex
                } else {
                    y.find(".data input").val("");
                    y.find("select[name='field']").focus()
                }
                q.datepickerFix === true && jQuery.fn.datepicker !== undefined && z.find(".hasDatepicker").each(function () {
                    var J = jQuery.data(this, "datepicker").settings;
                    y.find("#" + this.id).unbind().removeAttr("id").removeClass("hasDatepicker").datepicker(J)
                });
                y.find("select[name='field']").change(function (J) {
                    J.stopPropagation()
                });
                return false
            });
            b.find(".ui-search").click(function () {
                var z = jQuery(b.selector),
                    y, A = z.find("select[name='groupOp'] :selected").val();
                y = q.stringResult ? '{"groupOp":"' + A + '","rules":[' : {
                    groupOp: A,
                    rules: []
                };
                z.find(".sf").each(function (J) {
                    var M = jQuery(this).find("select[name='field'] :selected").val(),
                        U = jQuery(this).find("select[name='op'] :selected").val(),
                        ba = jQuery(this).find("input.vdata,select.vdata :selected").val();
                    ba += "";
                    ba = ba.replace(/\\/g, "\\\\").replace(/\"/g, '\\"');
                    if (q.stringResult) {
                        if (J > 0) y += ",";
                        y += '{"field":"' + M + '",';
                        y += '"op":"' + U + '",';
                        y += '"data":"' + ba + '"}'
                    } else y.rules.push({
                        field: M,
                        op: U,
                        data: ba
                    })
                });
                if (q.stringResult) y += "]}";
                q.onSearch(y);
                return false
            });
            b.find(".ui-reset").click(function (z, y) {
                z = jQuery(b.selector);
                z.find(".ui-del").click();
                z.find("select[name='groupOp']")[0].selectedIndex = 0;
                q.onReset(y);
                return false
            });
            b.find(".ui-add-last").click(function () {
                var z = jQuery(b.selector + " .sf:last"),
                    y = z.clone(true).insertAfter(z);
                y.find(".ui-state-default").removeClass("ui-state-hover ui-state-active");
                y.find(".data input").val("");
                y.find("select[name='field']").focus();
                q.datepickerFix === true && jQuery.fn.datepicker !== undefined && z.find(".hasDatepicker").each(function () {
                    var A =
                    jQuery.data(this, "datepicker").settings;
                    y.find("#" + this.id).unbind().removeAttr("id").removeClass("hasDatepicker").datepicker(A)
                });
                y.find("select[name='field']").change(function (A) {
                    A.stopPropagation()
                });
                return false
            });
            this.setGroupOp = function (z) {
                selDOMobj = b.find("select[name='groupOp']")[0];
                var y = {},
                    A = selDOMobj.options.length,
                    J;
                for (J = 0; J < A; J++) y[selDOMobj.options[J].value] = J;
                selDOMobj.selectedIndex = y[z];
                jQuery(selDOMobj).change(function (M) {
                    M.stopPropagation()
                })
            };
            this.setFilter = function (z) {
                var y = z.sfref;
                z = z.filter;
                var A = [],
                    J, M, U, ba, W = {};
                selDOMobj = y.find("select[name='field']")[0];
                J = 0;
                for (U = selDOMobj.options.length; J < U; J++) {
                    W[selDOMobj.options[J].value] = {
                        index: J,
                        ops: {}
                    };
                    A.push(selDOMobj.options[J].value)
                }
                J = 0;
                for (U = A.length; J < U; J++) {
                    if (selDOMobj = y.find(".ops > select[class='field" + J + "']")[0]) {
                        M = 0;
                        for (ba = selDOMobj.options.length; M < ba; M++) W[A[J]].ops[selDOMobj.options[M].value] = M
                    }
                    if (selDOMobj = y.find(".data > select[class='field" + J + "']")[0]) {
                        W[A[J]].data = {};
                        M = 0;
                        for (ba = selDOMobj.options.length; M < ba; M++) W[A[J]].data[selDOMobj.options[M].value] =
                        M
                    }
                }
                var ca, oa, wa, xa;
                A = z.field;
                if (W[A]) ca = W[A].index;
                if (ca != null) {
                    oa = W[A].ops[z.op];
                    if (oa === undefined) {
                        J = 0;
                        for (U = g.operators.length; J < U; J++) if (g.operators[J].op == z.op) {
                            oa = J;
                            break
                        }
                    }
                    wa = z.data;
                    xa = W[A].data == null ? -1 : W[A].data[wa]
                }
                if (ca != null && oa != null && xa != null) {
                    y.find("select[name='field']")[0].selectedIndex = ca;
                    y.find("select[name='field']").change();
                    y.find("select[name='op']")[0].selectedIndex = oa;
                    y.find("input.vdata").val(wa);
                    if (y = y.find("select.vdata")[0]) y.selectedIndex = xa;
                    return true
                } else
                return false
            }
        }
    }
    return new e(this, a, c)
};
jQuery.fn.searchFilter.version = "1.2.9";
jQuery.fn.searchFilter.defaults = {
    clone: true,
    datepickerFix: true,
    onReset: function (a) {
        alert("Reset Clicked. Data Returned: " + a)
    },
    onSearch: function (a) {
        alert("Search Clicked. Data Returned: " + a)
    },
    onClose: function (a) {
        a.hide()
    },
    groupOps: [
		{ op: "AND", text: "all" },
		// { op: "OR", text: "any" } // JM
	],
    operators: [
		{ op: "cn", text: "contains" },
	// JM
		//{ op: "bn", text: "does not begin with" },
		//{ op: "en", text: "does not end with" },
		//{ op: "ge", text: "is greater or equal to" },
		//{ op: "gt", text: "is greater than" },
		//{ op: "in", text: "is in" },
		//{ op: "le", text: "is less or equal to" },
		//{ op: "lt", text: "is less than" },
		//{ op: "nc", text: "does not contain" } 
		//{ op: "ne", text: "is not equal to" },
		//{ op: "ni", text: "is not in" },
	],
    matchText: "match",
    rulesText: "rules",
    resetText: "Reset",
    searchText: "Search",
    stringResult: true,
    windowTitle: "Search Rules",
    ajaxSelectOptions: {}
};

