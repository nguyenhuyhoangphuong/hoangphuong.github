using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using csdl.Models;

namespace csdl.Controllers.Client
{
    public class tksController : Controller
    {
        private csdl1Entities db = new csdl1Entities();

        // GET: tks
        public ActionResult Index()
        {
            return View(db.tks.ToList());
        }

        // GET: tks/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            tk tk = db.tks.Find(id);
            if (tk == null)
            {
                return HttpNotFound();
            }
            return View(tk);
        }

        // GET: tks/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: tks/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "idtk,hoten,taikhoan,mk,sdt,diachi")] tk tk)
        {
            if (ModelState.IsValid)
            {
                db.tks.Add(tk);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(tk);
        }

        // GET: tks/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            tk tk = db.tks.Find(id);
            if (tk == null)
            {
                return HttpNotFound();
            }
            return View(tk);
        }

        // POST: tks/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "idtk,hoten,taikhoan,mk,sdt,diachi")] tk tk)
        {
            if (ModelState.IsValid)
            {
                db.Entry(tk).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(tk);
        }

        // GET: tks/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            tk tk = db.tks.Find(id);
            if (tk == null)
            {
                return HttpNotFound();
            }
            return View(tk);
        }

        // POST: tks/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            tk tk = db.tks.Find(id);
            db.tks.Remove(tk);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
